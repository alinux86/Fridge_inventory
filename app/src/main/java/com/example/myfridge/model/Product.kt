package com.example.myfridge.model
import androidx.annotation.StringRes


data class Product(
    @StringRes val name: Int,
    @StringRes val quantity: Int,
    @StringRes val date: Int
)