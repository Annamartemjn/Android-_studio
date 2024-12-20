package com.example.smartlab.ui.Interface

import com.example.smartlab.ui.Data.EmailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SupabaseApiService {
    // Отправка email с подтверждением
    @POST("auth/v1/magiclink")
    suspend fun sendEmailConfirmation(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authHeader: String,
        @Body emailRequest: EmailRequest
    ): Response<Unit>

    // Дополнительный запрос для проверки кода
    @POST("auth/v1/verify-code")
    suspend fun verifyCode(
        @Header("apikey") apiKey: String,
        @Header("Authorization") authHeader: String,
        @Body emailRequest: EmailRequest
    ): Response<Unit>
}