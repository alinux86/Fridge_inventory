package com.example.myfridge.api

import com.example.myfridge.model.ProductInfo
import retrofit2.Call
import retrofit2.http.GET

interface apiService {

    @GET("/api/v2/product/{barcode}")
    fun getProductInfo() : Call<List<ProductInfo>>
}
