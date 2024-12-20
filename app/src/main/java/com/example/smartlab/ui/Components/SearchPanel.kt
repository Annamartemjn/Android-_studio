package com.example.smartlab.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.smartlab.R

@Composable
fun SearchPanel(modifier: Modifier = Modifier, ) {
    val (query, setQuery) = remember { mutableStateOf("") }

    OutlinedTextField(
        value = query, // Указываем текущее значение текста
        onValueChange = { newQuery ->
            setQuery(newQuery) // Обновляем текст
            // Передаем обновленный текст в callback
        },
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(8.dp)
            .background(Color.White),
        placeholder = {
            Text("Искать анализы") // Используем @Composable Text для placeholder
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.icons),
                contentDescription = "Search Icon"
            ) // Иконка через painterResource
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
    )
}

@Preview
@Composable
private fun SearchPanelPreview() {
    SearchPanel()
}