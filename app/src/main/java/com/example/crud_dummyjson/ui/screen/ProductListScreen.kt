package com.example.crud_dummyjson.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crud_dummyjson.domain.model.ProductModel
import com.example.crud_dummyjson.ui.section.EmptySection
import com.example.crud_dummyjson.ui.section.ErrorSection
import com.example.crud_dummyjson.ui.section.LoadingSection
import com.example.crud_dummyjson.ui.section.ProductListSection
import com.example.crud_dummyjson.viewModel.ProductViewModel
import kotlinx.coroutines.launch

// Pantalla de lista: arma las secciones según el estado del ViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onAddClick: () -> Unit,
    onProductClick: (ProductModel) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Al volver del formulario con éxito: mostrar el mensaje y limpiarlo
    LaunchedEffect(state.successMessage) {
        state.successMessage?.let { message ->
            scope.launch { snackbarHostState.showSnackbar(message) }
            viewModel.selectProduct(null) // limpia selección y mensajes
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Productos") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+", style = MaterialTheme.typography.headlineSmall)
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when {
            state.isLoading -> LoadingSection(modifier)

            state.errorMessage != null && state.products.isEmpty() -> ErrorSection(
                message = state.errorMessage!!,
                onRetry = viewModel::loadProducts,
                modifier = modifier
            )

            state.products.isEmpty() -> EmptySection("No hay productos", modifier)

            else -> ProductListSection(
                products = state.products,
                onProductClick = onProductClick,
                modifier = modifier
            )
        }
    }
}
