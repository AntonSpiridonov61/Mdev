package com.example.auctiontrainer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.R

@Composable
fun MainTheme(
    style: AppStyle = AppStyle.Green,
    paddingSize: AppSize = AppSize.Medium,
    textSize: AppSize = AppSize.Medium,
    corners: AppCorners = AppCorners.Rounded,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (darkTheme) {
        true -> {
            when (style) {
                AppStyle.Purple -> purpleDarkPalette
                AppStyle.Blue -> blueDarkPalette
                AppStyle.Orange -> orangeDarkPalette
                AppStyle.Red -> redDarkPalette
                AppStyle.Green -> greenDarkPalette
            }
        }
        false -> {
            when (style) {
                AppStyle.Purple -> purpleLightPalette
                AppStyle.Blue -> blueLightPalette
                AppStyle.Orange -> orangeLightPalette
                AppStyle.Red -> redLightPalette
                AppStyle.Green -> greenLightPalette
            }
        }
    }

    val typography = when(textSize) {
        AppSize.Small -> smallTypography
        AppSize.Medium -> mediumTypography
        AppSize.Big -> bigTypography
    }

    val shapes = AppShape(
        padding = when (paddingSize) {
            AppSize.Small -> 12.dp
            AppSize.Medium -> 16.dp
            AppSize.Big -> 20.dp
        },
        cornersStyle = when (corners) {
            AppCorners.Flat -> RoundedCornerShape(0.dp)
            AppCorners.Rounded -> RoundedCornerShape(8.dp)
        }
    )

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppTypography provides typography,
        LocalAppShape provides shapes,
        content = content
    )
}
