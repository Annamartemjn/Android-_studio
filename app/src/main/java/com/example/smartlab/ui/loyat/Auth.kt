package com.example.smartlab.ui.loyat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.controls.ui.theme.Components.TextInput
import com.example.pr30.ui.components.PrimaryButton
import com.example.smartlab.R
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import android.widget.Toast
import com.example.smartlab.ui.Data.EmailRequest
import com.example.smartlab.ui.Object.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.smartlab.ui.Interface.PreferencesHelper
import kotlinx.coroutines.withContext

@Composable
fun Auth(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var showNextScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp, 130.dp)
    ) {
        if (showNextScreen) {
            CodeVaricetion() // Второй экран
        } else {
            Row {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.emojies),
                    contentDescription = "",
                    modifier = Modifier
                        .width(32.dp)
                        .height(32.dp)
                )
                Text(
                    text = "Добро пожаловать!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(start = 16.dp),
                    fontFamily = FontFamily(Font(R.font.nunito_black))
                )
            }

            Spacer(Modifier.height(25.dp))
            Text(
                text = "Войдите, чтобы пользоваться функциями приложения",
                fontSize = 15.sp,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(64.dp))
            Text(
                text = "Вход по E-mail",
                fontSize = 14.sp,
                color = Color(0xFF7E7E9A)
            )

            Spacer(Modifier.height(4.dp))
            TextInput(
                placeholder = "example@mail.ru",
                value = email,
                onValueChange = { newValue ->
                    email = newValue
                    isEmailValid = validateEmail(newValue)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(32.dp))

            PrimaryButton(
                onClick = {
                    if (isEmailValid) {
                        if (isInternetAvailable(context)) {
                            sendEmailConfirmation(context, email) { success ->
                                if (success) {
                                    showNextScreen = true
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Ошибка отправки кода подтверждения",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Нет интернет-соединения",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                text = "Далее",
                enable = isEmailValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}

// Функция для проверки наличия интернет-соединения
fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork?.let { connectivityManager.getNetworkCapabilities(it) }
    return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}

// Функция для отправки e-mail подтверждения через Retrofit
fun sendEmailConfirmation(context: Context, email: String, onResult: (Boolean) -> Unit) {
    val apiService = RetrofitClient.apiService
    val emailRequest = EmailRequest(email)

    // Создаем объект для работы с SharedPreferences
    val preferencesHelper = PreferencesHelper(context)

    // Запускаем корутину для сетевого запроса
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.sendEmailConfirmation(
                apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk",  // Замените на ваш фактический API-ключ
                authHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk",  // Замените на ваш фактический auth token
                emailRequest = emailRequest
            )

            if (response.isSuccessful) {
                // Сохраняем email в SharedPreferences после успешной отправки
                preferencesHelper.saveEmail(email)

                withContext(Dispatchers.Main) {
                    onResult(true)  // Уведомляем о успешной отправке
                }
            } else {
                withContext(Dispatchers.Main) {
                    onResult(false)  // Ошибка при отправке
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                onResult(false)  // Ошибка в случае исключения
            }
        }
    }
}

// Валидация email
fun validateEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,}$".toRegex()
    return email.matches(emailRegex)
}
@Preview
@Composable
private fun AuthPreview() {
    Auth()
}