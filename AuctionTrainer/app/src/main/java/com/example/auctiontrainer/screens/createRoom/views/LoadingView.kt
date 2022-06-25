package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = AppTheme.colors.tintColor
        )
    }
}