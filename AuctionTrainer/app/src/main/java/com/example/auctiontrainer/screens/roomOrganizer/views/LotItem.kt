package com.example.auctiontrainer.screens.roomOrganizer.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.base.LotState
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun LotItem(
    name: String,
    state: String
) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = AppTheme.shapes.padding,
                vertical = AppTheme.shapes.padding / 2
            )
            .fillMaxWidth(),
        elevation = 3.dp,
        backgroundColor = AppTheme.colors.primaryBackground,
        shape = AppTheme.shapes.cornersStyle
    ) {
        Row(
            modifier = Modifier
                .padding(AppTheme.shapes.padding)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(.7f),
                text = name,
                style = AppTheme.typography.toolbar,
                color = AppTheme.colors.primaryText
            )

            Text(
                textAlign = TextAlign.Right,
                text = state,
                style = AppTheme.typography.toolbar,
                color = AppTheme.colors.secondaryText
            )
        }
    }
}