package com.example.smartlab.ui.Interface

import com.example.smartlab.ui.Data.Action
import com.example.smartlab.ui.Data.Category
import com.example.smartlab.ui.Data.Product
import retrofit2.http.GET

interface SupabaseApi {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("actions")
    suspend fun getActions(): List<Action>

    @GET("categories")
    suspend fun getCategories(): List<Category>
}