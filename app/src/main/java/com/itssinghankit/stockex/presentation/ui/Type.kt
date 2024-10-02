package com.itssinghankit.stockex.presentation.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itssinghankit.stockex.R

val Typography = Typography(

    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.cirka_bold)),
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.gilroy_regular)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 1.sp
    )

)
