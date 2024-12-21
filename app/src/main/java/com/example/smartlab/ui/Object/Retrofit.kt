package com.example.smartlab.ui.Object

import com.example.smartlab.ui.Interface.SupabaseApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private const val BASE_URL = "https://jrjotllhwudqekuscfav.supabase.co/rest/v1/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk") // Замените на ваш актуальный ключ
                .addHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk") // Supabase может также требовать этот заголовок
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .build()

    val instance: SupabaseApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SupabaseApi::class.java)
    }
}
