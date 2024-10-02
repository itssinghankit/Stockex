package com.itssinghankit.stockex.presentation.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Black05,
    primaryContainer = Black15,
    onPrimaryContainer = Grey96,
    inversePrimary = Black40,
    secondary = Black80,
    onSecondary = Black20,
    secondaryContainer = Black30,
    onSecondaryContainer = darkblack90,
    tertiary = Orange80,
    onTertiary = Orange20,
    tertiaryContainer = Orange30,
    onTertiaryContainer = Orange90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Black05,
    onBackground = Color.White,
    surface = Black05,
    onSurface = Black91,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = Black05,
    onSurfaceVariant = Black43,
    surfaceContainer = Color.Black,
    outline = Black13,
    outlineVariant = Grey10

)

private val LightColorScheme = lightColorScheme(
    primary = Black05,
    onPrimary = Color.White,
    primaryContainer = Black90,
    onPrimaryContainer = Black10,
    inversePrimary = Black80,
    secondary = Grey53,
    onSecondary = Color.White,
    secondaryContainer = Black90,
    onSecondaryContainer = Black10,
    tertiary = Orange50,
    onTertiary = Color.White,
    tertiaryContainer = Orange90,
    onTertiaryContainer = Orange10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Color.White,
    onSurface = BlueGrey31,
    surfaceVariant = Color.White,
    onSurfaceVariant = Grey65,
    surfaceContainer = Color.White,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    outline = Grey92,
    outlineVariant = Grey86,

)

@Composable
fun StockexTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}