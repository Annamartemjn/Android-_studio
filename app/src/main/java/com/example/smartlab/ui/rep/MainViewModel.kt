package com.example.smartlab.ui.rep


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.smartlab.ui.Data.Action
import com.example.smartlab.ui.Data.Category
import com.example.smartlab.ui.Data.Product
import com.example.smartlab.ui.Object.Retrofit
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _actions = MutableStateFlow<List<Action>>(emptyList())
    val actions: StateFlow<List<Action>> = _actions

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _products.value = Retrofit.instance.getProducts()
            _actions.value = Retrofit.instance.getActions()
            _categories.value = Retrofit.instance.getCategories()
        }
    }
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
    val cartItems: StateFlow<List<Product>> = _cartItems

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    fun toggleCartItem(product: Product) {
        _cartItems.value = if (_cartItems.value.contains(product)) {
            _cartItems.value - product
        } else {
            _cartItems.value + product
        }
        // Update the total price after toggling the item
        _totalPrice.value = _cartItems.value.sumOf { it.price }
    }
}