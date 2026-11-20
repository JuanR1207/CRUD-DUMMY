package com.example.crud_dummyjson.navegation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.crud_dummyjson.ui.screen.ProductFormScreen
import com.example.crud_dummyjson.ui.screen.ProductListScreen
import com.example.crud_dummyjson.viewModel.ProductViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Un solo ViewModel compartido por todas las pantallas:
    // así lo que se crea/edita en el formulario se ve en la lista
    val viewModel: ProductViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.PRODUCT_LIST
    ) {
        // Pantalla 1: lista de productos
        composable(AppScreens.PRODUCT_LIST) {
            ProductListScreen(
                viewModel = viewModel,
                onAddClick = {
                    viewModel.selectProduct(null)
                    navController.navigate(AppScreens.productForm())
                },
                onProductClick = { product ->
                    viewModel.selectProduct(product)
                    navController.navigate(AppScreens.productForm(product.id))
                }
            )
        }

        // Pantalla 2: formulario para crear / editar / eliminar
        composable(
            route = AppScreens.PRODUCT_FORM,
            arguments = listOf(
                navArgument(AppScreens.PRODUCT_ID) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt(AppScreens.PRODUCT_ID) ?: -1
            ProductFormScreen(
                viewModel = viewModel,
                productId = productId.takeIf { it != -1 },
                onBack = { navController.backToList() }
            )
        }
    }
}

// Vuelve a la lista solo si estamos en el formulario (evita salir de la app por doble "atrás")
private fun NavHostController.backToList() {
    if (currentDestination?.route == AppScreens.PRODUCT_FORM) {
        popBackStack()
    }
}
