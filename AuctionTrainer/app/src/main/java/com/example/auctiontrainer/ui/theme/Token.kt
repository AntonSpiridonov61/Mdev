package com.example.auctiontrainer.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class AppColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color
)

data class AppTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

data class AppShape(
    val padding: Dp,
    val cornersStyle: Shape
)

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        get() = LocalAppTypography.current

    val shapes: AppShape
        @Composable
        get() = LocalAppShape.current
}

enum class AppStyle {
    Purple, Orange, Blue, Red, Green
}

enum class AppSize {
    Small, Medium, Big
}

enum class AppCorners {
    Flat, Rounded
}

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("No font provided")
}

val LocalAppShape = staticCompositionLocalOf<AppShape> {
    error("No shapes provided")
}