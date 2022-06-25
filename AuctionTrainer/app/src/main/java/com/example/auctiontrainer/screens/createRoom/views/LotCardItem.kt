package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.ui.theme.AppTheme


@Composable
fun LotCardItem(
    model: LotModel,
    onDeleteLotCard: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = AppTheme.shapes.padding,
                vertical = AppTheme.shapes.padding / 2
            )
            .fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = AppTheme.colors.primaryBackground,
        shape = AppTheme.shapes.cornersStyle
    ) {
        Row(
            modifier = Modifier
                .padding(AppTheme.shapes.padding)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier.weight(.7f),
                text = model.title,
                style = AppTheme.typography.toolbar,
                color = AppTheme.colors.primaryText
            )

            Column() {
                Text(
                    text = model.type,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
                Text(
                    text = model.price.toString(),
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.secondaryText
                )
            }
        }
    }
}