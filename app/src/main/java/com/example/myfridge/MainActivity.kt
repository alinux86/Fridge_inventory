package com.example.myfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myfridge.adapters.ProductAdapter
import com.example.myfridge.adapters.ProductAdapterListener
import com.example.myfridge.data.Fridge
import com.example.myfridge.data.Product


class MainActivity : AppCompatActivity(), ProductAdapterListener  {

    lateinit var fridge: Fridge
    lateinit var adapter : ProductAdapter
    lateinit var layout : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout  = findViewById(R.id.activity_main_layout)
        val buttonActivityAdd : View = findViewById(R.id.floatingActionButtonAddElement)

        buttonActivityAdd.setOnClickListener {
            var intent = Intent(this, ActivityAddPersistentDataTest::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()


        layout.clearFocus()

        fridge = Fridge(filesDir)
        fridge.loadData()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = ProductAdapter(fridge.getData(), this)
        recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
//        var layout : View = findViewById(R.id.activity_main_layout)
        layout.clearFocus()
        fridge.saveData()
    }

    override fun onDeleteItem(product: Product, position: Int) {
        fridge.deleteItem(product)
        adapter.notifyItemRemoved(position)
        adapter.notifyItemRangeChanged(position, adapter.itemCount)
    }

    override fun onUpdateItem(newProduct: Product, position: Int) {
        fridge.updateItem(/*newProduct*/)
    }
}

