package com.example.myfridge.adapters

import android.content.Context
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.R

class ProductViewHolder(view : View, val context: Context) : RecyclerView.ViewHolder(view) {
    val name: EditText = view.findViewById(R.id.editTextProductName)
    val quantity : EditText = view.findViewById(R.id.editTextQuantity)
    val date : EditText = view.findViewById(R.id.editTextDate)
    /*val editTextQuantity: EditText = itemView.findViewById(R.id.editTextQuantity)
    val editTextDate: EditText = itemView.findViewById(R.id.editTextDate)
*/
    fun setupHolder(product: com.example.myfridge.data.Product) {
        // Bind data to the EditText fields
      /*  editTextProductName.setText(product.name)
        editTextQuantity.setText(product.quantity.toString())
        editTextDate.setText(product.date)*/
        name.setText(product.name)
        quantity.setText(product.quantity)
        date.setText(product.date)


    }
}
