package com.example.smartlab.ui.Components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlab.ui.Data.Product


@Composable
fun ServiceCard(
    modifier: Modifier = Modifier,
    product: Product,
    isAddedToCart: Boolean,
    onToggleCart: () -> Unit
) {
    // Убираем локальную переменную isAddedToCart и используем переданный параметр
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp, 25.dp)
            .background(Color.White)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(8.dp)), // Устанавливаем тень для карточки
        shape = RoundedCornerShape(8.dp) // Устанавливаем округленные углы
    ) {
        Row(modifier = Modifier.background(Color.White).padding(15.dp, 25.dp, 0.dp, 10.dp).fillMaxWidth()) {
            Text(
                text = product.name,
                fontSize = 16.sp,
                lineHeight = 20.sp,
                color = Color.Black
            )
            Spacer(Modifier.height(16.dp))
        }

        Row(modifier = Modifier.background(Color.White).padding(15.dp, 0.dp, 0.dp, 25.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${product.time} дней",
                    lineHeight = 20.sp,
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(7.dp))
                Text(
                    text = "${product.price} ₽",
                    lineHeight = 20.sp,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Button(
                    onClick = onToggleCart, // Вызовем функцию toggleCart из ViewModel
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAddedToCart) Color.White else Color(0xFF1A6FEE),
                        contentColor = if (isAddedToCart) Color(0xFF1A6FEE) else Color.White
                    ),
                    border = if (!isAddedToCart) null else BorderStroke(1.dp, Color(0xFF1A6FEE))
                ) {
                    Text(if (isAddedToCart) "Убрать" else "Добавить")
                }
            }
        }
    }
}


@Preview
@Composable
private fun ServiceCardPreview() {

}