package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun LotViewNotItems(
    onCreateClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(56.dp),
                imageVector = Icons.Filled.Info,
                tint = AppTheme.colors.controlColor,
                contentDescription = "No Items Found"
            )

            Text(
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
                text = "Список лотов пуст!",
                style = AppTheme.typography.body,
                color = AppTheme.colors.primaryText,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 22.dp, end = 22.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.tintColor
                ),
                onClick = onCreateClick
            ) {
                Text(
                    text = "Добавить",
                    style = AppTheme.typography.body,
                    color = Color.White
                )
            }
        }
    }
}