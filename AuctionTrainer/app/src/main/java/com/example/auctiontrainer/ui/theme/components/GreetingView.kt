package com.example.auctiontrainer.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun GreetingView(
    nickname: String
) {
    Column(
        modifier = Modifier.padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Приветсвую",
            style = AppTheme.typography.body,
            color = AppTheme.colors.secondaryText
        )
        Text(
            text = nickname,
            style = AppTheme.typography.toolbar,
            color = AppTheme.colors.primaryText
        )
    }
}