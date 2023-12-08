package com.example.myfridge.adapters

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.R
import com.example.myfridge.model.Product

class ProductViewHolder(view : View, val context: Context) : RecyclerView.ViewHolder(view) {
    val name: EditText = view.findViewById(R.id.editTextProductName)
    val quantity : EditText = view.findViewById(R.id.editTextQuantity)
    val date : EditText = view.findViewById(R.id.editTextDate)
    fun setupHolder(product: Product) {
        name.setText(product.name)
        quantity.setText(product.quantity)
        date.setText(product.date)
    }
}
