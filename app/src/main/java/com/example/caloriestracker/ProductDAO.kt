package com.example.caloriestracker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.caloriestracker.Models.PartDayTypeEnum
import com.example.caloriestracker.Models.ProductModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class  ProductDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addProduct(productEntity: ProductModel)

    @Query("Select * from `product-table`")
    abstract fun getAllProducts(): Flow<List<ProductModel>>

    @Update
    abstract suspend fun updateProduct(productEntity: ProductModel)

    @Delete
    abstract suspend fun deleteProduct(productEntity: ProductModel)

    @Query("Select * from `product-table` where id=:id")
    abstract fun getProductById(id:Int): Flow<ProductModel>

    @Query("Select * from `product-table` where `product-partdaytype`=:partDayType and `product-date`=:date")
    abstract fun getProductsByPartDayTypeAndDate(partDayType: PartDayTypeEnum, date: String): Flow<List<ProductModel>>

    @Query("Select * from `product-table` where `product-date`=:date")
    abstract fun getProductsByDate(date: String): Flow<List<ProductModel>>
}