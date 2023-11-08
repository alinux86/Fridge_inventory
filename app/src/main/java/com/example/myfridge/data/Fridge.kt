package com.example.myfridge.data

import com.example.myfridge.R
import com.example.myfridge.model.Product

class Fridge {
    fun allFridge(): List<Product> {
        return listOf(
            Product(name = R.string.apple, quantity = R.string.quantity_1, date = R.string.date_1 ),
            Product(name = R.string.fish, quantity = R.string.quantity_2, date = R.string.date_2),
            Product(name = R.string.chips, quantity = R.string.quantity_3, date = R.string.date_3),
            Product(name = R.string.carottes, quantity = R.string.quantity_4, date = R.string.date_4)
        )
    }
}