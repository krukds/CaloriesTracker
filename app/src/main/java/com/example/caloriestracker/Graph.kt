package com.example.caloriestracker

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var productDatabase: ProductDatabase

    val productRepository by lazy{
        ProductRepository(productDAO = productDatabase.productDAO())
    }

    fun provide(context: Context){
        productDatabase = Room.databaseBuilder(context, ProductDatabase::class.java, "productlist.db").build()
    }
}