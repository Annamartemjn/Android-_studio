package com.example.smartlab.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.controls.ui.theme.Components.TextButton
import com.example.controls.ui.theme.Components.TextInput
import com.example.pr30.ui.components.PrimaryButton
import com.example.smartlab.ui.loyat.PinCodeScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.smartlab.ui.loyat.Main
import com.example.smartlab.ui.rep.MainViewModel

@Composable
fun CreatePatient(modifier: Modifier = Modifier) {

    var firstName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    val genders = listOf("Мужской", "Женский")

    val navController = rememberNavController()
    // Указываем startDestination, который будет первым экраном при запуске
    NavHost(navController = navController, startDestination = "CreatePatient") {
        composable("CreatePatient") {
            CreatePatient() // Экран верификации кода
        }
        composable("Main") {
            val viewModel: MainViewModel = viewModel()
            Main(
                viewModel = viewModel,
                modifier = TODO(),
                navController = TODO()
            ) // Главный экран
        }
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp, 76.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Создание карты \nпациента",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                text = "Пропустить",
                onClick = { /* Обработка пропуска */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Без карты пациента вы не сможете заказать анализы. \nВ картах пациентов будут храниться результаты анализов вас и ваших близких.",
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Поля ввода
        TextInput(
            value = firstName,
            onValueChange = { firstName = it },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            placeholder = "Имя"
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextInput(
            value = middleName,
            onValueChange = { middleName = it },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            placeholder = "Отчество"
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextInput(
            value = lastName,
            onValueChange = { lastName = it },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            placeholder = "Фамилия"
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextInput(
            value = birthDate,
            onValueChange = { birthDate = it },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            placeholder = "Дата рождения"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Выбор пола
        Box(modifier = Modifier.fillMaxWidth()) {
            TextInput(
                value = gender,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable { expanded = true }, // Нажатие открывает меню
                enabled = false,
                placeholder = "Пол"
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genders.forEach { itemGender ->
                    DropdownMenuItem(
                        onClick = {
                            gender = itemGender
                            expanded = false
                        },
                        text = { Text(text = itemGender) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Кнопка создания карты
        val isButtonEnabled = firstName.isNotEmpty() && lastName.isNotEmpty() &&
                birthDate.isNotEmpty() && gender.isNotEmpty()

        PrimaryButton(
            text = "Создать",
            enable = isButtonEnabled,
            onClick = {
                navController.navigate("Main") {
                    // Этот блок может использоваться для настройки дополнительных параметров навигации, если нужно
                    popUpTo("CreatePatient") { inclusive = true } // Удаляем экран верификации из стека
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)

                .then(
                    if (!isButtonEnabled) Modifier.background(Color.LightGray) // Неактивная кнопка
                    else Modifier // Активная кнопка
                )
        )
    }
}

@Preview
@Composable
private fun CreatePatientPreview() {
    CreatePatient()
}