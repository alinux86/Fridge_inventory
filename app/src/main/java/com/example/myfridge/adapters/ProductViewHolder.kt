package com.example.myfridge.adapters

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.R
import com.example.myfridge.model.data.Products

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val editTextProductName: EditText = itemView.findViewById(R.id.editTextProductName)
    private val editTextQuantity: EditText = itemView.findViewById(R.id.editTextQuantity)
    private val editTextDate: EditText = itemView.findViewById(R.id.editTextDate)

    fun setupHolder(productItem: Products) {
        // Bind data to the EditText fields
        editTextProductName.setText(productItem.name)
        editTextQuantity.setText(productItem.quantity.toString())
        editTextDate.setText(productItem.date)
    }
}
