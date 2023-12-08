package com.example.myfridge.model

data class ProductInfo(
    val code: String?,
    val product: ProductApi?,
    val status: Int?,
    val status_verbose: String?
)

data class ProductApi(
    val brands: String?,
    val ecoscore_grade: String?,
    val product_name: String?
)
