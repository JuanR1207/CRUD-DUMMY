package com.example.crud_dummyjson.domain.useCase

import com.example.crud_dummyjson.domain.model.ProductModel
import com.example.crud_dummyjson.domain.repository.ProductRepository
import javax.inject.Inject

// Actualiza un producto existente
class UpdateProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(product: ProductModel): ProductModel = repository.updateProduct(product)
}
