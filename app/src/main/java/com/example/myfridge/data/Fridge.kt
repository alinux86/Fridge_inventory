package com.example.myfridge.data

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer

class Product(var name: String, var quantity: String, var date: String, var index: Int) {}

class Fridge(private var filesDir: File) {

    private var jsonDataString: String = ""
    private var productsListData: ArrayList<Product> = ArrayList()

    fun recalculateIndex() {
        for (i in 0..<productsListData.size) {
            productsListData[i].index = i
        }
    }

    fun loadData() {

        val filePath = File(filesDir, "data.json")
        var jsonString = ""
        if (filePath.exists()) {
            jsonString = filePath.readText()
        }

        if (jsonString != "") {
            jsonDataString = jsonString
            var jsonObject = JSONObject(jsonString)

            var dataArray = jsonObject.optJSONArray("data")
            if (dataArray != null) {
                for (i in 0..<dataArray.length()) {
                    var jsonObj = dataArray.optJSONObject(i)
                    var product = Product(
                        jsonObj.getString("productName"),
                        jsonObj.getString("quantity"),
                        jsonObj.getString("date"),
                        i
                    )
                    productsListData.add(product)
                }
            }
        }
    }

    fun saveData() {
        var jsonObjectContainer = JSONObject()
        var jsonDataArray = JSONArray()
        for (item in productsListData) {
            var jsonObjectItem = JSONObject()
            jsonObjectItem.put("productName", item.name)
            jsonObjectItem.put("quantity", item.quantity)
            jsonObjectItem.put("date", item.date)
            jsonDataArray.put(jsonObjectItem)
        }
        jsonObjectContainer.put("data", jsonDataArray)
        jsonDataString = jsonObjectContainer.toString()
        var output: Writer
        var file: File = File(filesDir, "data.json")

        output = BufferedWriter(FileWriter(file))
        output.write(jsonDataString)
        output.close()
    }

    fun getData(): ArrayList<com.example.myfridge.data.Product> {
        return productsListData
    }

    fun updateItem(newProduct: com.example.myfridge.data.Product) {
        saveData()
    }

    fun deleteItem(product: com.example.myfridge.data.Product) {
        productsListData.removeAt(product.index)
        recalculateIndex()
        saveData()
    }
}