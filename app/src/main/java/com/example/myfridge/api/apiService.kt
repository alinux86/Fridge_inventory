package com.example.myfridge.api

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
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


fun getProduct(
    productCode : String,
    productNameTextField: EditText,
    context: Context,
    progressBar: ProgressBar,
    buttonApi: Button,
    brandText: TextView,
    ecoscoreText: TextView) {
    progressBar.visibility = View.VISIBLE
    buttonApi.isEnabled = false
    val call = theApiService.getProductInfo(productCode)
    call.enqueue(object : Callback<ProductInfo> {
        override fun onResponse(call: Call<ProductInfo>, response: Response<ProductInfo>) {
            // Activer le bouton
            buttonApi.isEnabled = true
            progressBar.visibility = View.GONE
            if (response.isSuccessful) {
                response.body()?.let { productInfo ->
                    productNameTextField.setText(productInfo.product?.product_name)
                    // mise à jour textfield
                    brandText.text = "Brand: ${(productInfo.product?.brands)}"
                    ecoscoreText.text = "Ecoscore Grade: ${(productInfo.product?.ecoscore_grade)}"

                    brandText.visibility = if (productInfo.product?.brands.isNullOrBlank()) View.GONE else View.VISIBLE
                    ecoscoreText.visibility = if (productInfo.product?.ecoscore_grade.isNullOrBlank()) View.GONE else View.VISIBLE
                    showToastSuccess(context, "Product found!")
                }
            } else {
                if (response.code() == 404) {
                    // Traitement spécifique pour le code 404
                    showToast404(context, "Product not found. Please try again.")
                }
            }
        }
        override fun onFailure(call: Call<ProductInfo>, t: Throwable) {
            buttonApi.isEnabled = true
            progressBar.visibility = View.GONE
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