package com.example.myfridge.model
// String? allow nullable, in case there is no information about a val
//data class ProductInfo(
//    val product_name : String?,
//    val brands : String?,
//    val ecoscore_grade : String?
//)

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
