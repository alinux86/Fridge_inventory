package com.example.myfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.adapters.ProductAdapter
import com.example.myfridge.data.Fridge
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var fridge: Fridge

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonActivityAdd : View = findViewById(R.id.floatingActionButtonAddElement)

        buttonActivityAdd.setOnClickListener {
            var intent = Intent(this, ActivityAddPersistentDataTest::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.i("debug", "On resume")

        val filePath = File(filesDir, "data.json")
        var jsonString = ""
        if (filePath.exists()) {
            // Le fichier existe, vous pouvez le lire
            jsonString = filePath.readText()
            Log.i("MainActivity", jsonString)
            // Maintenant, vous avez le contenu du fichier JSON dans la variable "jsonString"
        }
        fridge = Fridge()
        fridge.loadData(jsonString)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        val adapter = ProductAdapter(fridge.getData(), this)
//        adapter.addContext(this)
        recyclerView.adapter = ProductAdapter(fridge.getData(), this)
    }

    fun deleteItem(position : Int) {
        fridge.deleteItem(position)
    }
}

