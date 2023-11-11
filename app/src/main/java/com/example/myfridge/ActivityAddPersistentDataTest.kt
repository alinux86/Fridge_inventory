package com.example.myfridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer

class Product(val name: String, val quantity: String, val date: String) {
}

class ActivityAddPersistentDataTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_layout)

        val nameText: TextView = findViewById(R.id.editTextTestName)
        val quantityText: TextView = findViewById(R.id.editTextTestQuantity)
        val dateText: TextView = findViewById(R.id.editTextTestDate)
        var logProductsText: TextView = findViewById(R.id.textViewItemsList)

        var buttonAdd: View = findViewById(R.id.buttonTestAddElement)
        buttonAdd.setOnClickListener {
            var newProduct = Product(
                nameText.text.toString(),
                quantityText.text.toString(),
                dateText.text.toString()
            )
            writeJson(newProduct)
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
        var logProductsText: TextView = findViewById(R.id.textViewItemsList)
        var valuesString = getJsonValues()
        logProductsText.text = valuesString
    }

    fun writeJson(product: Product) {
        Log.i("MainActivity", "writeJson called")

        var container = JSONArray()
        var json = JSONObject()

        val filePath = File(filesDir, "data.json")
        if (filePath.exists() && filePath.readText() != "") {
            // Le fichier existe, vous pouvez le lire
            var jsonString = filePath.readText()
            Log.i("MainActivity", "JSON String : $jsonString")
            container = JSONObject(jsonString).optJSONArray("data")
            // Maintenant, vous avez le contenu du fichier JSON dans la variable "jsonString"
        } else {
            // Le fichier n'existe pas, gérer le cas où le fichier est introuvable
            // Vous pouvez afficher un message d'erreur ou effectuer d'autres actions nécessaires.
//            val container = JSONArray()
//            val json = JSONObject()
        }

        val objContainer = JSONObject()
//        val container = JSONArray()
//        val json = JSONObject()


        json.put("productName", product.name)
        json.put("quantity", product.quantity)
        json.put("date", product.date)

        container.put(json)
        objContainer.put("data", container)

        var output: Writer
        var file: File = File(this.filesDir, "data.json")

        output = BufferedWriter(FileWriter(file))
        output.write(objContainer.toString())
        output.close()
        Toast.makeText(this, "Test ok", Toast.LENGTH_LONG).show()
        Log.i("MainActivity", "writeJson ended")
    }

    fun getJsonValues(): String {
        val filePath = File(filesDir, "data.json")
        var jsonString = ""
        if (filePath.exists()) {
            // Le fichier existe, vous pouvez le lire
            jsonString = filePath.readText()
            Log.i("MainActivity", jsonString)
            // Maintenant, vous avez le contenu du fichier JSON dans la variable "jsonString"
        }

        return jsonString
    }

    fun clearJson() {


        val filePath = File(filesDir, "data.json")
        if (filePath.exists()) {
            var output: Writer
            var file: File = File(this.filesDir, "data.json")

            output = BufferedWriter(FileWriter(file))
            output.write("")
            output.close()
            Toast.makeText(this, "Data cleared", Toast.LENGTH_LONG).show()

        }

    }
}
