package com.example.myfridge

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.myfridge.data.Fridge
import com.example.myfridge.data.NewProduct


class ActivityAddPersistentData : AppCompatActivity() {

    lateinit var fridge: Fridge
    lateinit var layout : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_layout)

        fridge = Fridge(filesDir)
        fridge.loadData()
        layout  = findViewById(R.id.activity_add_new_layout)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val nameText: EditText = findViewById(R.id.editTextNewProductName)
        val quantityText: EditText = findViewById(R.id.editTextNewProductQuantity)
        val dateText: EditText = findViewById(R.id.editTextNewProductDate)

        var buttonAdd: View = findViewById(R.id.buttonAddElement)

        dateText.setOnClickListener {
            layout.clearFocus()
            imm.hideSoftInputFromWindow(layout.windowToken, 0)
            val newFragment = DatePickerFragment.newInstance(dateText)

            newFragment.show(supportFragmentManager, "datePicker")
        }

        buttonAdd.setOnClickListener {
            var newProduct = NewProduct(
                nameText.text.toString(),
                quantityText.text.toString(),
                dateText.text.toString()
            )
            fridge.addItem(newProduct)
//            logData()
            finish()
        }

//        var buttonLog: View = findViewById(R.id.buttonTestLogElements)
//        buttonLog.setOnClickListener {
//            logData()
//        }

//        var buttonDelete: View = findViewById(R.id.buttonTestDelete)
//        buttonDelete.setOnClickListener {
//            clearJson()
//            logData()
//            finish()
//        }
//        logData()
    }

//    fun logData() {
//        var valuesString = fridge.getDataString()
//        var logProductsText: TextView = findViewById(R.id.textViewItemsList)
//        logProductsText.text = valuesString
//    }

//    fun clearJson() {
//        fridge.clearAll()
//    }
}
