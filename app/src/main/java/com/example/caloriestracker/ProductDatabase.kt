package com.example.caloriestracker

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.caloriestracker.Models.ProductModel

@Database(
    entities = [ProductModel::class],
    version = 1,
    exportSchema = false
)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun productDAO(): ProductDAO
}