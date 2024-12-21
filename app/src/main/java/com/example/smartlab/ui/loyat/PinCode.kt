package com.example.smartlab.ui.loyat

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.MessageDigest
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.smartlab.ui.layout.CreatePatient


@Composable
fun PinCodeScreen(navController1: NavHostController) {
    val context = LocalContext.current
    val navController = rememberNavController()

    // Указываем startDestination, который будет первым экраном при запуске
    NavHost(navController = navController, startDestination = "PinCodeScreen") {
        composable("PinCodeScreen") {
            PinCodeScreen(navController) // Экран верификации кода
        }
        composable("CreatePatient") {
            CreatePatient() // Главный экран
        }
    }
    // Состояние для хранения введенного PIN-кода
    var pinCode by remember { mutableStateOf("") }

    // Максимальная длина PIN-кода
    val maxPinLength = 4

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Заголовок
        Text(
            text = "Создайте пароль",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // Подзаголовок
        Text(
            text = "Для защиты ваших персональных данных",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Кружки для PIN-кода
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            for (i in 0 until maxPinLength) {
                val isFilled = i < pinCode.length
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .border(
                            BorderStroke(2.dp, Color.Blue),
                            CircleShape
                        )
                        .background(
                            if (isFilled) Color.Blue else Color.White,
                            CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Цифровая клавиатура
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val digits = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9"),
                listOf("", "0", "⌫")
            )

            digits.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { digit ->
                        if (digit.isNotEmpty()) {
                            Button(
                                onClick = {
                                    when (digit) {
                                        "⌫" -> {
                                            // Удалить последний символ
                                            if (pinCode.isNotEmpty()) {
                                                pinCode = pinCode.dropLast(1)
                                            }
                                        }
                                        else -> {
                                            // Добавить цифру, если не достигнут максимум
                                            if (pinCode.length < maxPinLength) {
                                                pinCode += digit
                                            }

                                            // Если введено 4 символа, захэшировать и сохранить
                                            if (pinCode.length == maxPinLength) {
                                                savePinCode(context, pinCode, navController)
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(4.dp),
                                shape = RoundedCornerShape(100.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF5F5F5),
                                    contentColor = Color.Black
                                )
                            ) {
                                Text(
                                    text = digit,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(80.dp)) // Пустое место
                        }
                    }
                }
            }
        }
    }
}

// Функция для хэширования строки в SHA-256
fun hashPinCode(pin: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(pin.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

// Функция для сохранения PIN-кода в EncryptedSharedPreferences
fun savePinCode(context: Context, pinCode: String, navController: NavController ) {
    val hashedPin = hashPinCode(pinCode)

    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    sharedPreferences.edit().putString("pin_code", hashedPin).apply()
    navController.navigate("CreatePatient") {
        // Этот блок может использоваться для настройки дополнительных параметров навигации, если нужно
        popUpTo("PinCodeScreen") { inclusive = true } // Удаляем экран верификации из стека
    }
}

@Preview
@Composable
private fun PinCodeScreenPreview() {

}