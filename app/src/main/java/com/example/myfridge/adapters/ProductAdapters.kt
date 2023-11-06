package com.example.myfridge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.R
import com.example.myfridge.model.Product

class ProductAdapter(val products: ArrayList<Product>): RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(layout, parent.context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.setupHolder(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
