package com.example.crud_dummyjson.navegation

// Rutas (direcciones) de cada pantalla de la app
object AppScreens {

    const val PRODUCT_LIST = "product_list"

    // productId es opcional: sin él se crea un producto, con él se edita
    const val PRODUCT_ID = "productId"
    const val PRODUCT_FORM = "product_form?$PRODUCT_ID={$PRODUCT_ID}"

    // Construye la ruta real para navegar al formulario
    fun productForm(productId: Int? = null): String =
        if (productId == null) "product_form" else "product_form?$PRODUCT_ID=$productId"
}
