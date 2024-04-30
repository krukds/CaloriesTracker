package com.example.caloriestracker.Models

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.android.parcel.Parcelize
import kotlin.math.round

data class PartDayModel(
    val partDayType: PartDayTypeEnum,
    val iconId: Int,
    val title: String,
    var productList: MutableList<ProductModel> = mutableStateListOf(),
    var isOpen: MutableState<Boolean> = mutableStateOf(false)
) {
    fun getTotalFats(): Double {
        var sum = 0.0;
        productList.forEach{product->
            sum += product.getFats()
        }
        return sum
    }
    fun getTotalCarbohydrates():Double {
        var sum = 0.0;
        productList.forEach{product->
            sum += product.getCarbohydrates()
        }
        return sum
    }
    fun getTotalProteins():Double {
        var sum = 0.0;
        productList.forEach{product->
            sum += product.getProteins()
        }
        return sum
    }
    fun getTotalCalories():Int {
        var sum = 0.0;
        productList.forEach{product->
            sum += product.getCalories()
        }
        return round(sum).toInt()
    }
    fun getTotalRDNS(totalRDNS: Int):Int {
        var sum = 0;
        productList.forEach{product->
            sum += product.getRDNS(totalRDNS)
        }
        return sum
    }
}

@Parcelize
enum class PartDayTypeEnum : Parcelable {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}