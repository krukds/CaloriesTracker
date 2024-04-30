package com.example.caloriestracker.Models

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Parcelize
@Entity(tableName = "product-table")
data class ProductModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name="product-name")
    private val _name: String,
    @ColumnInfo(name="product-grams")
    private var _grams: Int,
    @ColumnInfo(name="product-partdaytype")
    private val _partDayType: PartDayTypeEnum,
    @ColumnInfo(name="product-date")
    private val _date: String,
    @ColumnInfo(name="product-fats")
    private val _fats: Double,
    @ColumnInfo(name="product-carbohydrates")
    private val _carbohydrates: Double,
    @ColumnInfo(name="product-proteins")
    private val _proteins: Double,
    @ColumnInfo(name="product-calories")
    private val _calories: Double,
    @ColumnInfo(name="product-fiber")
    private val _fiber: Double
): Parcelable {
    fun getPartDayType(): PartDayTypeEnum {
        return _partDayType
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDate(): String {
        return _date
    }
    fun getName(): String {
        return _name
    }
    fun getRDNS(totalRDNS: Int): Int {
        return (getCalories() / totalRDNS * 100.0).toInt()
    }
    fun getGrams(): Int {
        return _grams
    }
    fun setGrams(grams: Int) {
        _grams = grams
    }
    fun getFats(): Double {
        return _fats * _grams / 100.0
    }
    fun getCarbohydrates(): Double {
        return _carbohydrates * _grams / 100.0
    }
    fun getProteins(): Double {
        return _proteins * _grams / 100.0
    }
    fun getCalories(): Double {
        return _calories * _grams / 100.0
    }
    fun getFiber(): Double {
        return _fiber * _grams / 100.0
    }
    fun getEnergy(): Double {
        return getCalories() * 4.2 * _grams / 100.0
    }
}

data class ProductResponseModel(
    val id: String,
    val title: String,
    val energy: String,
    val protein: String,
    val carbohydrate: String,
    val fat: String,
    val fiber: String?
)

data class ProductListResponseModel(val data: List<ProductResponseModel>)