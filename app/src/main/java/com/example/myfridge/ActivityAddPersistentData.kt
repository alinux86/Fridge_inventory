package com.example.myfridge

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.myfridge.api.getProduct
import com.example.myfridge.data.Fridge
import com.example.myfridge.data.NewProduct


class ActivityAddPersistentData : AppCompatActivity() {

    lateinit var fridge: Fridge
    lateinit var layout: View
    var stock: String = "Fridge"
    //lateinit val buttonApi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_layout)

        fridge = Fridge(filesDir)
        fridge.loadData()
        layout = findViewById(R.id.activity_add_new_layout)
        val intent: Intent = intent
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val nameText: EditText = findViewById(R.id.editTextNewProductName)
        val quantityText: EditText = findViewById(R.id.editTextNewProductQuantity)
        val dateText: EditText = findViewById(R.id.editTextNewProductDate)
        val productCodeText : EditText = findViewById(R.id.editTextBarcode)
        val brandText: TextView = findViewById(R.id.textViewBrand)
        val ecoscoreText: TextView = findViewById(R.id.textViewEcoscoreGrade)

        val stock: String? = intent.getStringExtra("Stock")

        var buttonAdd: View = findViewById(R.id.buttonAddElement)
        val buttonApi: Button = findViewById(R.id.buttonGetProduct) as Button


        dateText.setOnClickListener {
            layout.clearFocus()
            imm.hideSoftInputFromWindow(layout.windowToken, 0)
            val newFragment = DatePickerFragment.newInstance(dateText)

            newFragment.show(supportFragmentManager, "datePicker")
        }

        buttonAdd.setOnClickListener {
            var newProduct = NewProduct(
                name = nameText.text.toString(),
                quantity = quantityText.text.toString(),
                date = dateText.text.toString(),
                stock = this.stock
            )
            fridge.addItem(newProduct)
            logData()
            finish()
        }

        buttonApi.setOnClickListener{
            Log.i("contenu", "Call api")
            layout.clearFocus()
            imm.hideSoftInputFromWindow(layout.windowToken, 0)
            getProduct(productCodeText.text.toString(), nameText, this, progressBar, buttonApi, brandText, ecoscoreText)
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

    fun logData() {
        Log.i("debug", fridge.getDataString())
    }

//    fun clearJson() {
//        fridge.clearAll()
//    }

}
