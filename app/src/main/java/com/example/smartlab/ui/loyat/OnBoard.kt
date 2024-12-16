package com.example.smartlab.ui.loyat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controls.ui.theme.Components.OnboardDescription
import com.example.controls.ui.theme.Components.OnboardHeader
import com.example.controls.ui.theme.Components.TextButton
import com.example.smartlab.R

@Composable
fun OnBoard(modifier: Modifier = Modifier, buttontext: String, headerText:String, descriptionText:String, dodImg:ImageVector, pngImg: ImageBitmap) {
    Column (modifier = modifier.padding(20.dp)) {
        Row {

            TextButton(text = buttontext,
                modifier = Modifier
                    .weight(1f)

            )
            Image(
                ImageVector.vectorResource(R.drawable.shape),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
            )
        }
        Spacer(Modifier.height(61.dp))

        OnboardHeader(
            text = headerText,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(29.dp))
        OnboardDescription(
            text = descriptionText,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.height(60.dp))
        Image(
            dodImg,
            contentDescription = null,
            modifier = Modifier
                .width(58.dp)
                .height(14.dp)
                .align(Alignment.CenterHorizontally)

        )
        Spacer(Modifier.height(106.dp))
        Image(
            pngImg,
            contentDescription = null,
            modifier = Modifier
                .width(204.dp)
                .height(200.dp)
                .align(Alignment.CenterHorizontally)

        )

    }
}

@Preview
@Composable
private fun OnBoardPreview() {
    OnBoard(buttontext = "Пропустить", headerText = "Анализы", descriptionText = "Экспресс сбор и получение проб", dodImg = ImageVector.vectorResource(R.drawable.group2_1), pngImg = ImageBitmap.imageResource(R.drawable.image3))
}