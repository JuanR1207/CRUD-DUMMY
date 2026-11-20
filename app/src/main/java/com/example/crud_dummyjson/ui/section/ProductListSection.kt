package com.example.crud_dummyjson.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.crud_dummyjson.domain.model.ProductModel
import com.example.crud_dummyjson.ui.composable.ProductItem

// Sección con la lista de productos (cada uno es un ProductItem)
@Composable
fun ProductListSection(
    products: List<ProductModel>,
    onProductClick: (ProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        // padding inferior extra para que el botón "+" no tape el último producto
        contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // La key usa también el índice porque DummyJSON devuelve
        // el mismo id para todos los productos creados
        itemsIndexed(products, key = { index, item -> "${item.id}-$index" }) { _, product ->
            ProductItem(
                product = product,
                onClick = { onProductClick(product) }
            )
        }
    }
}
