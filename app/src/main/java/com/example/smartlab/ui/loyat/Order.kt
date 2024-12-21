package com.example.smartlab.ui.loyat

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.controls.ui.theme.Components.TextInput
import com.example.pr30.ui.components.PrimaryButton
import com.google.android.gms.location.FusedLocationProviderClient
import android.location.Location
import java.io.IOException
import java.util.Locale
import android.Manifest


@SuppressLint("MissingPermission")
@Composable
fun MakingAnOrder(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    val isButtonEnabled = address.isNotBlank() && phone.isNotBlank()

    // Запрос разрешений
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchAddress(context, fusedLocationClient) { resolvedAddress ->
                address = resolvedAddress ?: "Не удалось определить адрес"
            }
        } else {
            address = "Разрешение не предоставлено"
        }
    }

    // Автоматически получаем адрес при запуске
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchAddress(context, fusedLocationClient) { resolvedAddress ->
                address = resolvedAddress ?: "Не удалось определить адрес"
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Column(
        modifier = modifier
            .padding(start = 18.dp, top = 18.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Text(
            text = "Оформление заказа",
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.size(82.dp))

        // Поле адреса
        Column {
            Row {
                Text(
                    text = "Адрес",
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    color = Color.Gray
                )
                Text(
                    text = "*",
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    color = Color.Red
                )
            }
            TextInput(
                placeholder = "Введите ваш адрес",
                value = address,
                onValueChange = { newValue -> address = newValue },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.size(30.dp))

        // Поле телефона
        Column {
            Row {
                Text(
                    text = "Телефон",
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    color = Color.Gray
                )
                Text(
                    text = "*",
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    color = Color.Red
                )
            }
            TextInput(
                placeholder = "Введите ваш номер телефона",
                value = phone,
                onValueChange = { newValue -> phone = newValue },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        // Поле комментария
        Column {
            Row {
                Text(
                    text = "Комментарий",
                    fontSize = 24.sp,
                    lineHeight = 28.sp,
                    color = Color.Gray
                )
//                Image(
//                    painter = painterResource(id = R.drawable.male),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .width(20.dp)
//                        .height(24.dp)
//                )
            }
            TextInput(
                placeholder = "Можете оставить свои пожелания",
                value = comment,
                onValueChange = { newValue -> comment = newValue },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.weight(0.1f))

        // Кнопка "Заказать"
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                if (isButtonEnabled) {

                }
            },
            text = "Заказать",
            enable = isButtonEnabled
        )
    }
}

@SuppressLint("MissingPermission")
fun fetchAddress(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onAddressResult: (String?) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    onAddressResult(address)
                } else {
                    onAddressResult("Не удалось определить адрес")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                onAddressResult("Ошибка получения адреса")
            }
        } else {
            onAddressResult("Местоположение недоступно")
        }
    }
}