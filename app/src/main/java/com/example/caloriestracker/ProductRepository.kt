package com.example.caloriestracker

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.caloriestracker.Models.PartDayTypeEnum
import com.example.caloriestracker.Models.ProductModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ProductRepository(private val productDAO: ProductDAO) {
    suspend fun addProduct(product: ProductModel){
        productDAO.addProduct(product)
    }

    fun getProducts(): Flow<List<ProductModel>> {
        return productDAO.getAllProducts()
    }

    suspend fun updateProduct(product: ProductModel){
        productDAO.updateProduct(product)
    }

    suspend fun deleteProduct(product: ProductModel){
        productDAO.deleteProduct(product)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getProductsByPartDayTypeAndDate(partDayType: PartDayTypeEnum, date: LocalDate): Flow<List<ProductModel>> {
        return productDAO.getProductsByPartDayTypeAndDate(
            partDayType,
            date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getProductsByDate(date: LocalDate): Flow<List<ProductModel>> {
        return productDAO.getProductsByDate(
            date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        )
    }
}