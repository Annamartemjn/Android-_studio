package com.example.smartlab.ui.loyat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlab.ui.Components.NavButton
import com.example.smartlab.ui.Components.SearchPanel
import com.example.smartlab.ui.Components.ServiceCard
import com.example.smartlab.ui.rep.MainViewModel
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import com.example.smartlab.ui.Data.Action
import com.example.smartlab.ui.Data.Category
import com.example.smartlab.ui.Data.Product
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlab.R
import com.example.smartlab.ui.Components.ButtomCategories
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*


@Composable
fun Main(viewModel: MainViewModel, modifier: Modifier = Modifier.fillMaxSize().background(Color.White),navController: NavController) {
    val products: List<Product> by viewModel.products.collectAsState()
    val categories: List<Category> by viewModel.categories.collectAsState()
    val cartItems: List<Product> by viewModel.cartItems.collectAsState()
    val totalPrice: Double by viewModel.totalPrice.collectAsState()
    var searchQuery by remember { mutableStateOf("") }


    val navController = rememberNavController()
    // Указываем startDestination, который будет первым экраном при запуске
    NavHost(navController = navController, startDestination = "Main") {
        composable("Main") {
            val viewModel: MainViewModel = viewModel()
            Main(
                viewModel = viewModel,
                modifier = TODO(),
                navController = TODO()
            ) // Экран верификации кода
        }
        composable("Order") {
            MakingAnOrder() // Главный экран
        }
    }

    val filteredProducts = products.filter { it.name.contains(searchQuery, ignoreCase = true) }

    Column(
        modifier = modifier
    ) {
        LazyColumn(
            Modifier
                .background(Color.White)
                .weight(1f) // Используем .weight(1f), чтобы этот контейнер занимал доступное пространство
                .padding(20.dp, 55.dp, 20.dp, 25.dp)
        ) {
            item {
                SearchPanel()
            }
            item {
                Text(text = "Акции и новости", fontSize = 17.sp, lineHeight = 24.sp, color = Color.Gray)
            }
            item {
                Spacer(Modifier.height(16.dp))
            }
            // Баннеры
            item {
                LazyRow {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.banner),
                            contentDescription = "Banner",
                            modifier = Modifier
                                .width(270.dp)
                                .height(152.dp)
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.height(32.dp))
            }
            item {
                Text(text = "Каталог анализов", fontSize = 17.sp, lineHeight = 24.sp, color = Color.Gray)
            }
            item {
                Spacer(Modifier.height(16.dp))
            }
            item {
                LazyRow {
                    items(categories) { category ->
                        ButtomCategories(category) // Убедитесь, что компонент существует
                        Spacer(Modifier.width(16.dp))
                    }
                }
            }
            // Список продуктов
            items(filteredProducts) { product ->
                ServiceCard(
                    product = product,
                    isAddedToCart = cartItems.contains(product), // Получаем актуальное состояние
                    onToggleCart = { viewModel.toggleCartItem(product) } // Используем метод toggleCartItem из ViewModel
                )
            }
        }

        // Нижняя колонка с кнопкой, которая отображается, если есть товары в корзине
        if (cartItems.isNotEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(20.dp)

                    .height(110.dp)

            ) {
                Button(
                    onClick = {
                        navController.navigate("Order") {
                            // Этот блок может использоваться для настройки дополнительных параметров навигации, если нужно
                            popUpTo("CreatePatient") { inclusive = true } // Удаляем экран верификации из стека
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A6FEE)),
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.iconka),
                        contentDescription = "ikonka",
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "В корзину",
                        modifier = Modifier
                            .padding(16.dp)
                            ,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Left,
                        color = Color.Black
                    )
                    Text(
                        text = "${totalPrice} ₽",
                        modifier = Modifier
                            .padding(16.dp)
                            ,
                        fontSize = 17.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Right,
                        color = Color.Black
                    )
                }
            }
        }

        // Навигационная кнопка
        NavButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
        )
    }
}


@Preview
@Composable
private fun MainPreview() {

}