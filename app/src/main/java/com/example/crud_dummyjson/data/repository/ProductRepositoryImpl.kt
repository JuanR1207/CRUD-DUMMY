package com.example.crud_dummyjson.data.repository

import com.example.crud_dummyjson.data.mapper.toDomain
import com.example.crud_dummyjson.data.remote.api.ProductService
import com.example.crud_dummyjson.data.remote.dto.product.UpdateProductRequest
import com.example.crud_dummyjson.domain.model.ProductModel
import com.example.crud_dummyjson.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductService
) : ProductRepository {
    override suspend fun getProducts(): List<ProductModel> =
        api.getProducts().products.map{ it.toDomain() }

    override suspend fun getProductBYId(id: Int): ProductModel =
        api.getProductById(id).toDomain()

    override suspend fun createProduct(product: ProductModel): ProductModel =
        api.addProduct(product.toRequest()).toDomain()

    override suspend fun updateProduct(product: ProductModel): ProductModel =
        api.putProductById(product.id, product.toRequest()).toDomain()

    override suspend fun deleteProduct(id: Int): Boolean {
        api.deleteProductById(id)
        return true
    }

    private fun ProductModel.toRequest() = UpdateProductRequest(

        title = title,
        description = description,
        category = category,
        price = price
    )
}