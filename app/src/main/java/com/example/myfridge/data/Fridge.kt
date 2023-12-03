package com.example.myfridge.data

import com.example.myfridge.model.NewProduct
import com.example.myfridge.model.Product
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Fridge(private var filesDir: File) {

    private var jsonDataString: String = ""
    private var productsListData: ArrayList<Product> = ArrayList()

    fun recalculateIndex() {
        productsListData.sortWith(Comparator { product1, product2 ->
            val date1 = product1.date
            val date2 = product2.date

            when {
                date1.isNullOrEmpty() && date2.isNullOrEmpty() -> 0 // Les deux dates sont vides, considérez-les égales
                date1.isNullOrEmpty() -> -1 // La première date est vide, placez-la avant
                date2.isNullOrEmpty() -> 1 // La deuxième date est vide, placez-la avant
                else -> {
                    // Les deux dates sont non vides, comparez-les
                    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    val parsedDate1 = dateFormat.parse(date1) ?: Date(0)
                    val parsedDate2 = dateFormat.parse(date2) ?: Date(0)
                    parsedDate1.compareTo(parsedDate2)
                }
            }
        })
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
                        name = jsonObj.getString("productName"),
                        quantity = jsonObj.getString("quantity"),
                        date = jsonObj.getString("date"),
                        stock = jsonObj.getString("stock"),
                        index = i,
                    )
                    productsListData.add(product)
                }
                recalculateIndex()
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
            jsonObjectItem.put("stock", item.stock)
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
                stock = newProduct.stock,
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
            string += "${item.name}\n\tQty: ${item.quantity}\tDate: ${item.date}\tIndex: ${item.index}\tStock:${item.stock}\n"
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

