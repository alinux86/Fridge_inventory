package com.example.myfridge.model

import androidx.annotation.StringRes

//
//data class Product(
//    @StringRes val name: Int,
//    @StringRes val quantity: Int,
//    @StringRes var date: Int
//)
data class Product(
    var name: String,
    var quantity: String,
    var date: String,
    val stock: String,
    var index: Int
)

data class NewProduct(
    val name: String,
    val quantity: String,
    val date: String,
    val stock: String
)