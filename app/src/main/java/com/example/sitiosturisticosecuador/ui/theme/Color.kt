package com.example.sitiosturisticosecuador.ui.theme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// En Color.kt o dentro de tu Theme.kt
val PrimaryGreen = Color(0xFF2E7D32)
val SecondaryGold = Color(0xFFF9A825)
val BackgroundLight = Color(0xFFF7F9F6)

// Dentro de tu LightColorScheme en Theme.kt:
private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    secondary = SecondaryGold,
    background = BackgroundLight,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black
)