package com.example.myfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.adapter = ProductAdapter(Fridge().allFridge())

        val buttonActivityAdd : View = findViewById(R.id.buttonTestActivityAddNew)
        buttonActivityAdd.setOnClickListener {
            var intent = Intent(this, ActivityAddPersistentDataTest::class.java)
            startActivity(intent)
        }

    }


}

