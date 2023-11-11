package com.example.myfridge.data

import android.util.Log
import com.example.myfridge.R
import com.example.myfridge.model.Product
import org.json.JSONObject

class ProductTest() {
    lateinit var name: String
    lateinit var quantity: String
    lateinit var date: String
    lateinit var index: String
}


class Fridge {

    var jsonData: String = ""
    var jsonObject: JSONObject = JSONObject()
    var productsListData: ArrayList<ProductTest> = ArrayList()
    fun allFridge(): List<Product> {
        return listOf(
            Product(name = R.string.apple, quantity = R.string.quantity_1, date = R.string.date_1),
            Product(name = R.string.fish, quantity = R.string.quantity_2, date = R.string.date_2),
            Product(name = R.string.chips, quantity = R.string.quantity_3, date = R.string.date_3),
            Product(
                name = R.string.carottes,
                quantity = R.string.quantity_4,
                date = R.string.date_4
            )
        )
    }


    fun loadData(jsonString: String) {
        if (jsonString != "") {
            jsonData = jsonString
            jsonObject = JSONObject(jsonString)
            Log.i("debug", "Load data. Json object : ${jsonObject.toString()}")
//        lateinit var listProducts: ArrayList<ProductTest>

            var dataArray = jsonObject.optJSONArray("data")
            if (dataArray != null) {
                for (i in 0..<dataArray.length()) {
                    Log.i("debug", dataArray.toString())
                    var jsonObj = dataArray.optJSONObject(i)
                    var product: ProductTest = ProductTest()
                    product.name = jsonObj.getString("productName")
                    product.quantity = jsonObj.getString("quantity")
                    product.date = jsonObj.getString("date")
                    product.index = i.toString()
                    productsListData.add(product)
                }
            }
        }
    }

    fun getData(): ArrayList<ProductTest> {
        return productsListData
    }

    fun deleteItem(position : Int) {
        Log.i("debug", "Json content : ${jsonObject.toString()}")
        var array = jsonObject.optJSONArray("data")
        Log.i("debug", "Array content : ${array.toString()}")
        array.remove(position)
        Log.i("debug", "Json content : ${jsonObject.toString()}")
    }
}