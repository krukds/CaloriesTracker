package com.example.caloriestracker.ViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriestracker.Graph
import com.example.caloriestracker.Models.PartDayModel
import com.example.caloriestracker.Models.PartDayTypeEnum
import com.example.caloriestracker.Models.ProductModel
import com.example.caloriestracker.ProductRepository
import com.example.caloriestracker.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class DiaryViewModel(
    private val productRepository: ProductRepository = Graph.productRepository
): ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _dateState = mutableStateOf(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)

    val dateState: State<LocalDate> = _dateState

    private val _partDayListState: MutableList<PartDayModel> = mutableStateListOf(
        PartDayModel(PartDayTypeEnum.BREAKFAST, R.drawable.sunrise, "Сніданок"),
        PartDayModel(PartDayTypeEnum.LUNCH, R.drawable.sun, "Обід"),
        PartDayModel(PartDayTypeEnum.DINNER, R.drawable.sunrise_afternoon, "Вечеря"),
        PartDayModel(PartDayTypeEnum.SNACK, R.drawable.moon, "Перекуси/Інше"),
    )
    val partDayListState = _partDayListState
    var getProducts: MutableState<Flow<List<ProductModel>>> = mutableStateOf(emptyFlow())

    init {
        viewModelScope.launch{
            getProducts.value = productRepository.getProductsByDate(dateState.value)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate(date: LocalDate) {
        _dateState.value = date
        viewModelScope.launch{
            getProducts.value = productRepository.getProductsByDate(_dateState.value)
        }
    }


    fun saveProduct(product: ProductModel){
        viewModelScope.launch(Dispatchers.IO) {
            if(product.id != null) {
                productRepository.updateProduct(product)
            } else {
                productRepository.addProduct(product)
            }
        }
    }


    fun deleteProduct(product: ProductModel){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteProduct(product)
        }
    }

}