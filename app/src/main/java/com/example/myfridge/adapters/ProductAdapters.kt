package com.example.myfridge.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.DatePickerFragment
import com.example.myfridge.R
import com.example.myfridge.data.Product

interface ProductAdapterListener {
    fun onDeleteItem(product: Product, position: Int)
    fun onUpdateItem(newProduct: Product, position: Int)
}

class ProductAdapter(
    val products: ArrayList<Product>,
    val listener: ProductAdapterListener,
    val fragmentManager: FragmentManager
) :
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

        var nameTextView: EditText = holder.itemView.findViewById(R.id.editTextProductName)
        var quantityTextView: EditText = holder.itemView.findViewById(R.id.editTextQuantity)
        var dateTextView: EditText = holder.itemView.findViewById(R.id.editTextDate)
        var deleteButton = holder.itemView.findViewById<ImageView>(R.id.imageView)

        nameTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                product.name = nameTextView.text.toString()
                listener.onUpdateItem(product, position)
//                nameTextView.clearFocus()
            }
        }
        quantityTextView.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                product.quantity = quantityTextView.text.toString()
                listener.onUpdateItem(product, position)
            }
        }
        dateTextView.setOnClickListener {
            val newFragment = DatePickerFragment.newInstance(dateTextView, product)
            newFragment.show(fragmentManager, "datePicker")
        }
        dateTextView.doAfterTextChanged {
            product.date = dateTextView.text.toString()
            listener.onUpdateItem(product, position)
        }
        deleteButton.setOnClickListener {
            listener.onDeleteItem(product, position)
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }
}
