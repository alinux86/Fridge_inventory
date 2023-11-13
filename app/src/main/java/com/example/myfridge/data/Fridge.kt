package com.example.myfridge.data

import android.util.Log
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer

class Product(var name: String, var quantity: String, var date: String, var index: Int) {}
class NewProduct(val name: String, val quantity: String, val date: String) {}

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

    fun getData(): ArrayList<Product> {
        return productsListData
    }

    fun addItem(newProduct: NewProduct) {
        var maxIndex = 0
        if (productsListData.size > 0) {
            maxIndex = productsListData.maxBy { it.index }.index
        }
        var product =
            Product(
                name = newProduct.name,
                quantity = newProduct.quantity,
                date = newProduct.date,
                index = maxIndex + 1
            )
        productsListData.add(product)
        recalculateIndex()
        saveData()
    }

    fun updateItem(/*newProduct: Product*/) {
        saveData()
    }

    fun deleteItem(product: Product) {
        productsListData.removeAt(product.index)
        recalculateIndex()
        saveData()
    }

    fun getDataString(): String {
        var string = ""
        for (item in productsListData) {
            string+="${item.name}\n\tQty: ${item.quantity}\tDate: ${item.date}\tIndex: ${item.index}\n"
        }
        return string
    }

    fun clearAll() {
        val file = File(filesDir, "data.json")
        if (file.exists()) {
            var output: Writer

            output = BufferedWriter(FileWriter(file))
            output.write("")
            output.close()
        }
    }
    fun length(): Int {
        return productsListData.size
    }
}