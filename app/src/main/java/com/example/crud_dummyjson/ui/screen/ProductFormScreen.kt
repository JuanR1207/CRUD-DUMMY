package com.example.crud_dummyjson.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crud_dummyjson.domain.model.ProductModel
import com.example.crud_dummyjson.ui.composable.DeleteConfirmDialog
import com.example.crud_dummyjson.ui.section.EmptySection
import com.example.crud_dummyjson.ui.section.ProductFormSection
import com.example.crud_dummyjson.viewModel.ProductViewModel

// Pantalla de formulario: crear (productId = null) o editar/eliminar (productId != null)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    viewModel: ProductViewModel,
    productId: Int?,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isEditing = productId != null
    val product = remember(productId, state.products) {
        productId?.let { id -> state.products.firstOrNull { it.id == id } }
    }

    // Cuando el ViewModel avisa que se guardó/eliminó bien: volver a la lista
    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar producto" else "Nuevo producto") },
                navigationIcon = {
                    IconButton(onClick = onBack, enabled = !state.isSaving) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        }
    ) { innerPadding ->
        if (isEditing && product == null) {
            EmptySection("Producto no encontrado", Modifier.padding(innerPadding))
        } else {
            ProductFormContent(
                product = product,
                isSaving = state.isSaving,
                errorMessage = state.errorMessage,
                onSave = { edited ->
                    if (isEditing) viewModel.updateProduct(edited) else viewModel.createProduct(edited)
                },
                onDelete = viewModel::deleteProduct,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun ProductFormContent(
    product: ProductModel?,
    isSaving: Boolean,
    errorMessage: String?,
    onSave: (ProductModel) -> Unit,
    onDelete: (ProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    // Valores de los campos (no se pierden al girar la pantalla)
    var title by rememberSaveable(product?.id) { mutableStateOf(product?.title.orEmpty()) }
    var description by rememberSaveable(product?.id) { mutableStateOf(product?.description.orEmpty()) }
    var category by rememberSaveable(product?.id) { mutableStateOf(product?.category.orEmpty()) }
    var price by rememberSaveable(product?.id) { mutableStateOf(product?.price?.toString().orEmpty()) }
    var showErrors by rememberSaveable { mutableStateOf(false) }
    var showDeleteConfirm by rememberSaveable { mutableStateOf(false) }

    // Validaciones
    val priceValue = price.replace(',', '.').toDoubleOrNull()
    val titleError = title.isBlank()
    val categoryError = category.isBlank()
    val priceError = priceValue == null || priceValue < 0

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProductFormSection(
            title = title, onTitleChange = { title = it },
            description = description, onDescriptionChange = { description = it },
            category = category, onCategoryChange = { category = it },
            price = price, onPriceChange = { price = it },
            titleError = showErrors && titleError,
            categoryError = showErrors && categoryError,
            priceError = showErrors && priceError,
            enabled = !isSaving
        )

        // Error que viene de la API (por ejemplo, sin internet)
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Eliminar: solo al editar
            if (product != null) {
                OutlinedButton(
                    onClick = { showDeleteConfirm = true },
                    enabled = !isSaving,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) { Text("Eliminar") }
            }

            Button(
                enabled = !isSaving,
                onClick = {
                    showErrors = true
                    if (!titleError && !categoryError && !priceError) {
                        val base = product ?: ProductModel(id = 0, title = "", description = "", category = "", price = 0.0)
                        onSave(
                            base.copy(
                                title = title.trim(),
                                description = description.trim(),
                                category = category.trim(),
                                price = priceValue!!
                            )
                        )
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                } else {
                    Text(if (product != null) "Guardar" else "Crear")
                }
            }
        }
    }

    if (showDeleteConfirm && product != null) {
        DeleteConfirmDialog(
            productTitle = product.title,
            onConfirm = {
                showDeleteConfirm = false
                onDelete(product)
            },
            onDismiss = { showDeleteConfirm = false }
        )
    }
}
