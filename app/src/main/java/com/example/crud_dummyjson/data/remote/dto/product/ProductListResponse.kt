package com.example.crud_dummyjson.data.remote.dto.product

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductListResponse(
    val products: List<ProductX> = emptyList()
)