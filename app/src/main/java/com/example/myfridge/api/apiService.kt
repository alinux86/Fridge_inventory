package com.example.myfridge.api

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.myfridge.model.ProductInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface apiService {
    @GET("/api/v2/product/{barcode}?fields=product_name,brands,ecoscore_grade")
    fun getProductInfo(@Path("barcode") barcode: String): Call<ProductInfo>
}

private val retrofit by lazy {
    Retrofit.Builder().baseUrl("https://world.openfoodfacts.net/").addConverterFactory(
        GsonConverterFactory.create()
    ).build()
}

private val theApiService by lazy { retrofit.create(apiService::class.java) }


fun getProduct(productCode : String, productNameTextField: EditText, context: Context, progressBar: ProgressBar) {
    progressBar.visibility = View.VISIBLE
    val call = theApiService.getProductInfo(productCode)
    call.enqueue(object : Callback<ProductInfo> {
        override fun onResponse(call: Call<ProductInfo>, response: Response<ProductInfo>) {
            progressBar.visibility = View.GONE
            Log.i("contenu", "Raw response: ${response}")
            if (response.isSuccessful) {
                if (response.code() == 404) {
                    Log.i("contenu", "Raw response: ${response.code()}")
                    showToast404(context, "Product not found. Please try again.")
                } else {
                    response.body()?.let { productInfo ->
                        Log.i("contenu", "onResponse : ${productInfo.product?.product_name}")
                        productNameTextField.setText(productInfo.product?.product_name)
                        Log.i("contenu", "Raw response : ${response.body()}")
                        showToastSuccess(context, "Product found!")
                    }
                }
            }
        }

        override fun onFailure(call: Call<ProductInfo>, t: Throwable) {
            progressBar.visibility = View.GONE
            Log.e("MainActivity", "Failed to get result: ${t.message}")
            showToastError (context, "Error in the request: ${t.message}")
        }
    })
}


private fun showToastError(context: Context, message : String) {
    Toast.makeText(context , message, Toast.LENGTH_SHORT).show()
}

private fun showToastSuccess(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

private fun showToast404(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}