package com.example.myfridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.adapters.ProductAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader
import java.io.Writer


//import com.example.myfridge.data.Fridge


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.adapter = ProductAdapter(Fridge().allFridge())

        var button : View= findViewById(R.id.button2)
        button.setOnClickListener {
            writeJson("test")
        }
        getJsonValues()

    }

    fun writeJson(string : String) {
        Log.i("MainActivity", "writeJson called")

        var container = JSONArray()
        var json = JSONObject()

        val filePath = File(filesDir, "data.json")
        if (filePath.exists()) {
            // Le fichier existe, vous pouvez le lire
            var jsonString = filePath.readText()
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


        json.put("productName", "Café44")
        json.put("quantity", "5")
        json.put("date", "21.12.2023")

        container.put(json)
        objContainer.put("data", container)

        var output : Writer
        var file: File = File(this.filesDir, "data.json")

        output = BufferedWriter(FileWriter(file))
        output.write(objContainer.toString())
        output.close()
        Toast.makeText(this, "Test ok", Toast.LENGTH_LONG).show()
        Log.i("MainActivity", "writeJson ended")
    }

    fun getJsonValues() {
        val filePath = File(filesDir, "data.json")
        if (filePath.exists()) {
            // Le fichier existe, vous pouvez le lire
            var jsonString = filePath.readText()
            Log.i("MainActivity", jsonString)
            // Maintenant, vous avez le contenu du fichier JSON dans la variable "jsonString"
        } else {
            // Le fichier n'existe pas, gérer le cas où le fichier est introuvable
            // Vous pouvez afficher un message d'erreur ou effectuer d'autres actions nécessaires.
        }

    }
}

