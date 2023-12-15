package com.example.myfridge

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.myfridge.api.getProduct
import com.example.myfridge.data.Fridge
import com.example.myfridge.model.NewProduct


class ActivityAddPersistentData : AppCompatActivity() {

    lateinit var fridge: Fridge
    lateinit var layout: View
    var stock: String = "Fridge"

    lateinit var nameText: EditText
    lateinit var quantityText: EditText
    lateinit var dateText: EditText
    lateinit var productCodeText: EditText

    lateinit var buttonAdd: View
    lateinit var buttonApi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_layout)

        fridge = Fridge(filesDir)
        fridge.loadData()
        layout = findViewById(R.id.activity_add_new_layout)
        val intent: Intent = intent
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        nameText = findViewById(R.id.editTextNewProductName)
        quantityText = findViewById(R.id.editTextNewProductQuantity)
        dateText = findViewById(R.id.editTextNewProductDate)
        productCodeText = findViewById(R.id.editTextBarcode)
        val brandText: TextView = findViewById(R.id.textViewBrand)
        val ecoscoreText: TextView = findViewById(R.id.textViewEcoscoreGrade)

        //val stock: String? = intent.getStringExtra("Stock")

        buttonAdd = findViewById(R.id.buttonAddElement)
        buttonApi = findViewById<Button>(R.id.buttonGetProduct)

        buttonAdd.isEnabled = false
        buttonApi.isEnabled = false

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
            finish()
        }

        //Add change event listeners
        productCodeText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setApiButtonState()
            }
        })
        nameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setAddButtonState()
            }
        })
        quantityText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setAddButtonState()
                if (!s.isNullOrEmpty()) {
                    rejectValueZero()
                }
            }
        })
        dateText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setAddButtonState()
            }
        })

        buttonApi.setOnClickListener {
            layout.clearFocus()
            imm.hideSoftInputFromWindow(layout.windowToken, 0)
            getProduct(
                productCodeText.text.toString(),
                nameText,
                this,
                progressBar,
                buttonApi,
                brandText,
                ecoscoreText
            )
        }
    }

    fun setApiButtonState() {
        buttonApi.isEnabled = !productCodeText.text.toString().isNullOrBlank()
    }
    fun setAddButtonState() {
        buttonAdd.isEnabled =
            !nameText.text.toString().isNullOrBlank() && !quantityText.text.toString()
                .isNullOrBlank() && !dateText.text.toString().isNullOrBlank()
    }
    fun rejectValueZero() {
        var value = quantityText.text.toString().toInt()
        if (value == 0) {
            Toast.makeText(this.baseContext , "La quantité ne peux pas être égale à 0", Toast.LENGTH_SHORT).show()
            quantityText.setText("1")
            quantityText.setSelection(1)
        }
    }
}