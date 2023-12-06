package com.example.myfridge.adapters

import android.graphics.Color
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
import com.example.myfridge.model.Product
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
        holder.adapterPosition

        var product: Product = products[position]

        var nameTextView: EditText = holder.itemView.findViewById(R.id.editTextProductName)
        var quantityTextView: EditText = holder.itemView.findViewById(R.id.editTextQuantity)
        var dateTextView: EditText = holder.itemView.findViewById(R.id.editTextDate)
        var deleteButton = holder.itemView.findViewById<ImageView>(R.id.imageView)

        var defaultColor :Int =dateTextView.textColors.defaultColor

        isDateExpired(product.date)
            setDateTextColor(dateTextView, product, defaultColor)

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
            setDateTextColor(dateTextView, product, defaultColor)
        }
        deleteButton.setOnClickListener {
            listener.onDeleteItem(product, holder.adapterPosition)

        }
    }

    fun setDateTextColor(dateTextView : EditText, product: Product, defaultColor: Int) {
//        Log.i("debug", "setDateTextColor")
        if (isDateExpired(product.date)) {
            dateTextView.setTextColor(Color.parseColor("red"))
        } else {
//            Log.i("debug", "defaultColor")
            dateTextView.setTextColor(defaultColor)
        }
    }
    fun isDateExpired(dateString: String): Boolean {
        var isDateExpired = false
        if (!dateString.isNullOrEmpty()) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val today = calendar.time
//            Log.i("debug", "Today $today")
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val parsedDate = dateFormat.parse(dateString) ?: Date(0)
//            Log.i("debug", "ParsedDate $parsedDate")
            if (parsedDate <= today) {
                isDateExpired = true
            }
//            Log.i("debug", "isDateExpired ? $isDateExpired")
        }
        return isDateExpired
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
