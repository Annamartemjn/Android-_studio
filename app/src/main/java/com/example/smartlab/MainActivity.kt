package com.example.smartlab

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlab.ui.loyat.Auth
import com.example.smartlab.ui.loyat.Main
import com.example.smartlab.ui.loyat.OnBoard
import com.example.smartlab.ui.rep.MainViewModel
import com.example.smartlab.ui.theme.SmartLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

        // Проверяем состояние из SharedPreferences
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val isOnBoardingComplete = sharedPreferences.getBoolean("isOnBoardingComplete", false)

        setContent {
            SmartLabTheme {
//                if (isOnBoardingComplete) {
//                    // Показываем основной контент
//                    Auth()
//                } else {
//
//                    // Показываем экран онбординга с использованием Scaffold
//                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                        HorizontalPager(state = rememberPagerState(pageCount = { 3 }), userScrollEnabled = true) { page ->
//                            when (page) {
//                                0 -> OnBoard(
//                                    modifier = Modifier.padding(innerPadding),
//                                    buttontext = "Пропустить",
//                                    headerText = "Анализы",
//                                    descriptionText = "Экспресс сбор и получение проб",
//                                    dodImg = ImageVector.vectorResource(R.drawable.group2_1),
//                                    pngImg = ImageBitmap.imageResource(R.drawable.image3),
//                                    context = this@MainActivity,
//                                    onButtonClick = {
//                                        sharedPreferences.edit().putBoolean("isOnBoardingComplete", true).apply()
//                                        recreate() // Перезапуск Activity для обновления UI
//                                    }
//                                )
//                                1 -> OnBoard(
//                                    modifier = Modifier.padding(innerPadding),
//                                    buttontext = "Пропустить",
//                                    headerText = "Уведомления",
//                                    descriptionText = "Вы быстро узнаете о результатах",
//                                    dodImg = ImageVector.vectorResource(R.drawable.group2_2),
//                                    pngImg = ImageBitmap.imageResource(R.drawable.image2),
//                                    context = this@MainActivity,
//                                    onButtonClick = {
//                                        sharedPreferences.edit().putBoolean("isOnBoardingComplete", true).apply()
//                                        recreate() // Перезапуск Activity для обновления UI
//                                    }
//                                )
//                                2 -> OnBoard(
//                                    modifier = Modifier.padding(innerPadding),
//                                    buttontext = "Завершить",
//                                    headerText = "Мониторинг",
//                                    descriptionText = "Наши врачи всегда наблюдают\nза вашим здоровьем",
//                                    dodImg = ImageVector.vectorResource(R.drawable.group2_3),
//                                    pngImg = ImageBitmap.imageResource(R.drawable.image1),
//                                    context = this@MainActivity,
//                                    onButtonClick = {
//                                        sharedPreferences.edit().putBoolean("isOnBoardingComplete", true).apply()
//                                        recreate() // Перезапуск Activity для обновления UI
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
                val viewModel: MainViewModel = viewModel()
                Main(
                    viewModel = viewModel,
                    modifier = TODO(),
                    navController = TODO()
                )
            }
        }
    }
}

