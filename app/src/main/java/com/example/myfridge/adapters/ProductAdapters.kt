package com.example.myfridge.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.R
import com.example.myfridge.data.Product

interface ProductAdapterListener {
    fun onDeleteItem(product: Product, position: Int)
    fun onUpdateItem(newProduct: Product, position: Int)
}

class ProductAdapter(val products: ArrayList<Product>, val listener: ProductAdapterListener) :
    RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(layout, parent.context)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.setupHolder(products[position])

        var product: Product = products[position]

        var nameTextView: TextView = holder.itemView.findViewById(R.id.editTextProductName)
        var quantityTextView: TextView = holder.itemView.findViewById(R.id.editTextQuantity)
        var dateTextView: TextView = holder.itemView.findViewById(R.id.editTextDate)
        var deleteButton = holder.itemView.findViewById<ImageView>(R.id.imageView)

        nameTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                Log.i("debug", "Name focus event. Position $position")
                product.name = nameTextView.text.toString()
                listener.onUpdateItem(product, position)
            }
        }
        quantityTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                product.quantity = quantityTextView.text.toString()
                listener.onUpdateItem(product, position)
            }
        }
        dateTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                product.date = dateTextView.text.toString()
                listener.onUpdateItem(product, position)
            }
        }
        deleteButton.setOnClickListener {
            listener.onDeleteItem(product, position)
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }
}
