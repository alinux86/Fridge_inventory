package com.example.myfridge.api

import android.util.Log
import android.widget.EditText
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

fun getProduct(productCode : String, productNameTextField: EditText) {
    val call = theApiService.getProductInfo(productCode)
    call.enqueue(object : Callback<ProductInfo> {
        override fun onResponse(call: Call<ProductInfo>, response: Response<ProductInfo>) {
            Log.i("contenu", "Raw response: ${response}")
            if (response.isSuccessful) {
                response.body()?.let {
                    productInfo -> Log.i("contenu", "onResponse : ${productInfo.product?.product_name}")
                    productNameTextField.setText(productInfo.product?.product_name)
                    Log.i("contenu", "Raw response : ${response.body()}")
                }
//
            }
        }

        override fun onFailure(call: Call<ProductInfo>, t: Throwable) {
            Log.e("MainActivity", "Failed to get result: ${t.message}")
        }
    })
}