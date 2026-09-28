package me.basehub.scannerapp.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.basehub.scannerapp.R

val plusJakartaSans = FontFamily(
    Font(
        resId = R.font.plus_jakarta_sans_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.plus_jakarta_sans_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.plus_jakarta_sans_semi_bold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.plus_jakarta_sans_bold,
        weight = FontWeight.Bold,
    ),
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),

    headlineMedium = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),

    headlineSmall = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),

    bodyMedium = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
    ),

    bodySmall = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
    ),

    labelLarge = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    labelMedium = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),

    labelSmall = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.2.sp,
    ),

    titleLarge = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
    ),

    titleMedium = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),

    titleSmall = TextStyle(
        fontFamily = plusJakartaSans,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
)