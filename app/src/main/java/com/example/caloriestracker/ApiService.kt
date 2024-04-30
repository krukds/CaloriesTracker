package com.example.caloriestracker

import com.example.caloriestracker.Models.ProductListResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.tablycjakalorijnosti.com.ua")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val productsService = retrofit.create(ApiService::class.java)


interface ApiService {
    @GET("foodstuff/filter-list")
    suspend fun getProducts(
        @Query("query") query: String
    ): ProductListResponseModel
}