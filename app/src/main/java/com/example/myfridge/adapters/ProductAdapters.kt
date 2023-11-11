package com.example.myfridge.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.R
import com.example.myfridge.data.ProductTest

class ProductAdapter(val products: ArrayList<ProductTest>, val context: Context) :
    RecyclerView.Adapter<ProductViewHolder>() {

//    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(layout, parent.context)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.setupHolder(products[position])
        var deleteButton = holder.itemView.findViewById<ImageView>(R.id.imageView)
        deleteButton.setOnClickListener {
            Log.i("debug", "Delete position $position")

        }

    }

    override fun getItemCount(): Int {
        return products.size
    }

//    fun addContext(context: Context) {
//        this.context = context
//    };
}
