package com.example.crud_dummyjson.data.mapper

import com.example.crud_dummyjson.data.remote.dto.product.ProductX
import com.example.crud_dummyjson.domain.model.ProductModel

fun ProductX.toDomain(): ProductModel = ProductModel(
    id = id,
    title = title,
    description = description,
    category = category,
    price = price,
    imageUrl = thumbnail.ifBlank { images.firstOrNull().orEmpty() }
)