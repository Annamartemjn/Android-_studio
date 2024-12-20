package com.example.smartlab.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartlab.R


@Composable
fun NavButton(modifier: Modifier = Modifier) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(Color.White)
    ){

            Button(
                onClick = {},
                modifier = Modifier
                    .width(32.dp)
                    .weight(1f)
                    .background(Color.White),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F9)),
            ) {
                Column(
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.analiz),
                        contentDescription = "Analix",
                        alignment = Alignment.Center,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "Анализы",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = Color(0xFF1A6FEE)
                    )
                }
            }

        Button(
            onClick = {},
            modifier = Modifier
                .width(32.dp)
                .weight(1f)
                .background(Color.White),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F9)),
        ) {
            Column {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.result),
                    alignment = Alignment.Center,
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Анализы",
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFFB8C1CC)
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .width(32.dp)
                .weight(1f)
                .background(Color.White),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F9)),
        ) {
            Column {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.support),
                    alignment = Alignment.Center,
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Анализы",
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFFB8C1CC)
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .width(32.dp)
                .weight(1f)
                .background(Color.White),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5F9)),
        ) {
            Column {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.user),
                    alignment = Alignment.Center,
                    contentDescription = "Back",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Анализы",
                    modifier = Modifier,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFFB8C1CC)
                )
            }
        }
    }

}

@Preview
@Composable
private fun NavButtonPreview() {
    NavButton()
}
