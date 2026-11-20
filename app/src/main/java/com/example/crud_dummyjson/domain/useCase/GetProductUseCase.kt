package com.example.crud_dummyjson.domain.useCase

import com.example.crud_dummyjson.domain.model.ProductModel
import com.example.crud_dummyjson.domain.repository.ProductRepository
import javax.inject.Inject

// Obtiene un producto por su id
class GetProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<ProductModel> = repository.getProducts()
    suspend fun byId(id: Int): ProductModel = repository.getProductBYId(id)
}
