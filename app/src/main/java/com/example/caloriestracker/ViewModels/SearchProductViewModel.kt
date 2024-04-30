package com.example.caloriestracker.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caloriestracker.Models.ProductResponseModel
import com.example.caloriestracker.productsService
import kotlinx.coroutines.launch

class SearchProductViewModel: ViewModel() {
    private val _searchProductsState = mutableStateOf(ProductsState())
    val searchProductsState: State<ProductsState> = _searchProductsState

    fun fetchSearchProducts(query: String) {
        viewModelScope.launch {
            try{
                val response = productsService.getProducts(query)
                _searchProductsState.value = _searchProductsState.value.copy(
                    loading = false,
                    list = response.data,
                    error = null
                )
            } catch (e:Exception) {
                _searchProductsState.value = _searchProductsState.value.copy(
                    loading = false,
                    error = "Error fetching Products ${e.message}"
                )
            }
        }
    }

    fun loading() {
        _searchProductsState.value = _searchProductsState.value.copy(
            loading = true,
        )
    }

    fun clearSearchProducts() {
        _searchProductsState.value = _searchProductsState.value.copy(
            loading = false,
            list = emptyList(),
            error = null
        )
    }

    data class ProductsState(
        var loading: Boolean = false,
        val list: List<ProductResponseModel> = emptyList(),
        val error: String? = null
    )
}