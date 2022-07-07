package com.example.auctiontrainer.screens.roomTeam.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.MainTheme

@Composable
fun LotItem(
    lot: LotModel,
    currentLot: Int,
    idLot: Int
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .padding(
                horizontal = AppTheme.shapes.padding / 2,
                vertical = AppTheme.shapes.padding
            ),
        elevation = if (currentLot == idLot) 28.dp else 2.dp,
        backgroundColor = if (currentLot == idLot) AppTheme.colors.primaryBackground else AppTheme.colors.secondaryBackground,
        shape = AppTheme.shapes.cornersStyle
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = AppTheme.shapes.padding,
                    vertical = AppTheme.shapes.padding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = lot.title,
                style = AppTheme.typography.toolbar,
                color = AppTheme.colors.primaryText
            )
            Text(
                text = lot.type,
                style = AppTheme.typography.body,
                color = AppTheme.colors.secondaryText
            )
            Text(
                text = lot.startPrice.toString(),
                style = AppTheme.typography.body,
                color = AppTheme.colors.primaryText
            )
            Text(
                text = lot.limitPrice.toString(),
                style = AppTheme.typography.body,
                color = AppTheme.colors.primaryText
            )
        }
    }
}

@Preview
@Composable
fun LotItemPreview() {
    MainTheme() {
        LotItem(lot = LotModel(), currentLot = 1, idLot = 1)
    }
}