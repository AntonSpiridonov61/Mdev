package com.example.auctiontrainer.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val smallTypography = AppTypography(
    heading = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    ),
    body = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal
    ),
    toolbar = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    ),
    caption = TextStyle(
        fontSize = 10.sp
    )
)

val mediumTypography = AppTypography(
    heading = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    ),
    body = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    toolbar = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium
    ),
    caption = TextStyle(
        fontSize = 12.sp
    )
)

val bigTypography = AppTypography(
    heading = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold
    ),
    body = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal
    ),
    toolbar = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium
    ),
    caption = TextStyle(
        fontSize = 14.sp
    )
)

//val typography = AppTypography(
//    heading = TextStyle(
//        fontSize = when (textSize) {
//            AppSize.Small -> 24.sp
//            AppSize.Medium -> 28.sp
//            AppSize.Big -> 32.sp
//        },
//        fontWeight = FontWeight.Bold
//    ),
//    body = TextStyle(
//        fontSize = when (textSize) {
//            AppSize.Small -> 14.sp
//            AppSize.Medium -> 16.sp
//            AppSize.Big -> 18.sp
//        },
//        fontWeight = FontWeight.Normal
//    ),
//    toolbar = TextStyle(
//        fontSize = when (textSize) {
//            AppSize.Small -> 14.sp
//            AppSize.Medium -> 16.sp
//            AppSize.Big -> 18.sp
//        },
//        fontWeight = FontWeight.Medium
//    ),
//    caption = TextStyle(
//        fontSize = when (textSize) {
//            AppSize.Small -> 10.sp
//            AppSize.Medium -> 12.sp
//            AppSize.Big -> 14.sp
//        }
//    )
//)