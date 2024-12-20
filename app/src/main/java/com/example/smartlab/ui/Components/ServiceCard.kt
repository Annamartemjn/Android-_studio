package com.example.smartlab.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.security.Provider.Service

@Composable
fun ServiceCard(

) {
    var isAddedToCart by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    Text(text = "g",
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        color = Color.Black
                    )
                }
                Spacer(Modifier.height(16.dp))
                Row {
                    Column(Modifier.weight(1f)) {
                        Text(text = "1 день",
                            lineHeight = 20.sp,
                            fontSize = 14.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(text = "12200 ₽",
                            lineHeight = 20.sp,
                            fontSize = 14.sp,
                            color = Color.Black)
                    }
                    Button(onClick = {
                        isAddedToCart = !isAddedToCart

                    }) {
                        Text(if (isAddedToCart) "Убрать" else "Добавить")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ServiceCardPreview() {
    ServiceCard()
}