package com.example.smartlab.ui.loyat

import android.content.Context
import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlab.R
import com.example.smartlab.ui.Data.EmailRequest
import com.example.smartlab.ui.Interface.PreferencesHelper
import com.example.smartlab.ui.Object.RetrofitClient
import com.example.smartlab.ui.rep.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.*


@Composable
fun CodeVaricetion(modifier: Modifier = Modifier.background(Color.White)) {
    var inputValues = remember { mutableStateListOf("", "", "", "", "", "") }
    var focusedIndex by remember { mutableStateOf(0) }
    var showNextScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val navController = rememberNavController()

    // Указываем startDestination, который будет первым экраном при запуске
    NavHost(navController = navController, startDestination = "codeVerification") {
        composable("codeVerification") {
            CodeVaricetion(navController as Modifier) // Экран верификации кода
        }
        composable("pincode") {
            PinCodeScreen(navController) // Главный экран
        }
    }

    // Таймер состояния
    var timer by remember { mutableStateOf(59) }
    var isTimerFinished by remember { mutableStateOf(false) }

    // Запуск таймера только один раз
    LaunchedEffect(Unit) {
        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timer = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                timer = 0
                isTimerFinished = true
            }
        }.start()
    }

    // Проверка, если все поля заполнены
    val isCodeComplete = inputValues.all { it.isNotEmpty() }

    // Когда все поля заполнены, вызываем verifyCode
    if (isCodeComplete) {
        val email = PreferencesHelper(context).getEmail() ?: ""
        val enteredCode = inputValues.joinToString("") // Собираем введённый код из всех полей
        verifyCode(context, email, enteredCode, navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Кнопка "Назад"
        Button(
            onClick = {},
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .padding(start = 20.dp),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F9)),
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(132.dp))

        // Заголовок
        Text(
            text = "Введите код из E-mail",
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Поля для ввода кода
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0 until 6) {
                CodeInputField(
                    value = inputValues[i],
                    onValueChange = { newValue ->
                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                            inputValues[i] = newValue
                            if (newValue.isNotEmpty() && i < 5) focusedIndex = i + 1
                        }
                    },
                    isFocused = focusedIndex == i,
                    onFocus = { focusedIndex = i }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Таймер или кликабельный текст
        if (isTimerFinished) {
            Text(
                text = "Отправить код повторно",
                fontSize = 14.sp,
                color = Color.Blue,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable {
                    // Перезапуск таймера
                    isTimerFinished = false
                    timer = 59
                    // Извлекаем сохранённый email
                    val preferencesHelper = PreferencesHelper(context)
                    val savedEmail = preferencesHelper.getEmail()  // Получаем email из SharedPreferences

                    // Отправка нового email для подтверждения
                    if (savedEmail != null) {
                        EmailConfirmation(savedEmail)
                    }
                }
            )
        } else {
            Text(
                text = "Отправить код повторно можно будет через $timer секунд",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Функция для перезапуска таймера и отправки повторного кода на email
fun EmailConfirmation(email: String) {
    val apiService = RetrofitClient.apiService
    val emailRequest = EmailRequest(email)

    // Запускаем корутину для сетевого запроса
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.sendEmailConfirmation(
                apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk",  // Replace with your actual API key
                authHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk",  // Replace with your actual auth token
                emailRequest = emailRequest
            )

        } catch (e: Exception) {
            e.printStackTrace()

        }
    }
}

// Функция для проверки введенного кода
fun verifyCode(context: Context, email: String, enteredCode: String, navController: NavController) {
    // Извлекаем apiKey и authHeader из SharedPreferences
    val preferencesHelper = PreferencesHelper(context)
    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk"// Замените на ваш фактический API-ключ
    val authHeader = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Impyam90bGxod3VkcWVrdXNjZmF2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzQ0MTYwMTYsImV4cCI6MjA0OTk5MjAxNn0.c_Ju428hRkWMYI9Tz-OQ8KMXcRC3Rs3g6cIFLGDR5wk" // Замените на ваш фактический auth token

    // Если не получены apiKey или authHeader, обработаем ошибку
    if (apiKey == null || authHeader == null) {
        showError("Ошибка: API ключ или токен не найдены")
        return
    }

    // Собираем код в строку
    val enteredCodeString = enteredCode

    // Создаем объект запроса
    val emailRequest = EmailRequest(email)

    // Запускаем сетевой запрос в корутине
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitClient.apiService.verifyCode(apiKey, authHeader, emailRequest)

            if (response.isSuccessful) {
                navController.navigate("pincode") {
                    // Этот блок может использоваться для настройки дополнительных параметров навигации, если нужно
                    popUpTo("codeVerification") { inclusive = true } // Удаляем экран верификации из стека
                }
                // Код успешно проверен
                println("Код проверен успешно")
                 // Переход к следующему экрану
            } else {
                // Ошибка в проверке кода
                showError("Неверный код")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            showError("Ошибка при проверке кода")
        }
    }
}

// Отображение ошибки
fun showError(message: String) {
    // Показать ошибку, например через Toast или в UI
    println(message)  // Пример через println
}

// Переход на экран для ввода PIN-кода


// CodeInputField remains the same

@Composable
fun CodeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    isFocused: Boolean,
    onFocus: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .size(50.dp)
            .padding(2.dp),
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F5F5),
            focusedContainerColor = Color(0xFFFFFFFF),
            cursorColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = { onFocus() }
        )
    )
}

@Preview
@Composable
private fun CodeVaricetionPreview() {
    CodeVaricetion()
}