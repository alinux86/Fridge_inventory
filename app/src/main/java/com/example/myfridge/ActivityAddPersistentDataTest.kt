package com.example.myfridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.myfridge.data.Fridge
import com.example.myfridge.data.NewProduct
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer



class ActivityAddPersistentDataTest : AppCompatActivity() {

    lateinit var fridge: Fridge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_layout)

        fridge = Fridge(filesDir)
        fridge.loadData()

        val nameText: TextView = findViewById(R.id.editTextNewProductName)
        val quantityText: TextView = findViewById(R.id.editTextNewProductQuantity)
        val dateText: TextView = findViewById(R.id.editTextNewProductDate)

        var buttonAdd: View = findViewById(R.id.buttonTestAddElement)

        dateText.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }

        buttonAdd.setOnClickListener {
            var newProduct = NewProduct(
                nameText.text.toString(),
                quantityText.text.toString(),
                dateText.text.toString()
            )
            fridge.addItem(newProduct)
            logData()
            finish()
        }

        var buttonLog: View = findViewById(R.id.buttonTestLogElements)
        buttonLog.setOnClickListener {
            logData()
        }

        var buttonDelete: View = findViewById(R.id.buttonTestDelete)
        buttonDelete.setOnClickListener {
            clearJson()
            logData()
            finish()
        }
        logData()
    }

    fun logData() {
        var valuesString = fridge.getDataString()
        var logProductsText: TextView = findViewById(R.id.textViewItemsList)
        logProductsText.text = valuesString
    }

    fun clearJson() {
        fridge.clearAll()
    }
}
