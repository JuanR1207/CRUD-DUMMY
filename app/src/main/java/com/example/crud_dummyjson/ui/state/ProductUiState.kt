package com.example.crud_dummyjson.ui.state

import com.example.crud_dummyjson.domain.model.ProductModel

// Estado de la pantalla de productos
data class ProductUiState(
    val isLoading: Boolean = false,
    val products: List<ProductModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedProduct: ProductModel? = null,
    val isSaving: Boolean = false,
    val successMessage: String? = null
    )
