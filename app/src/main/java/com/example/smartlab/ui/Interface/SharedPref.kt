package com.example.smartlab.ui.Interface

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Сохранение email
    fun saveEmail(email: String) {
        sharedPreferences.edit().putString("email", email).apply()
    }

    // Извлечение email
    fun getEmail(): String? = sharedPreferences.getString("email", null)
}