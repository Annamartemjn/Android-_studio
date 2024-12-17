package com.example.smartlab.ui.loyat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun Auth(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize(1f).background(Color.White)
        .padding(20.dp,  100.dp)){
        Row() {
            Image(
               bitmap =  ImageBitmap.imageResource(
                   R.drawable.emojies
               ),
                contentDescription = "",
                modifier = Modifier.width(32.dp).height(32.dp)
            )
            Text(
                text = "Добро пожаловать!",
                fontSize = 24.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 16.dp),
                fontFamily = FontFamily(Font(R.font.nunito_black))


            )
        }
        Spacer(Modifier.height(25.dp))
        Text(text = "Войдите, чтобы пользоваться функциями приложения",
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.height(64.dp))
        Text(text = "Вход по E-mail",
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF7E7E9A)
        )
        Spacer(Modifier.height(4.dp))
        TextInput(
            placeholder = "example@mail.ru",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(32.dp))
        PrimaryButton(onClick = {

        },
            text="Далее",
            modifier = Modifier.fillMaxWidth().height(56.dp))
    }
}

@Preview
@Composable
private fun AuthPreview() {
    Auth()
}