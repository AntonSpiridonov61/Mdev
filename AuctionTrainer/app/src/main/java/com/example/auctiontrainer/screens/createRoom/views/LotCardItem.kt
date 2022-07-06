package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.MainTheme


@Composable
fun LotCardItem(
    model: LotModel,
    onDeleteLotCard: (LotModel) -> Unit
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
                .padding(
                    horizontal = AppTheme.shapes.padding,
                    vertical = AppTheme.shapes.padding / 2
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = model.title,
                style = AppTheme.typography.toolbar,
                color = AppTheme.colors.primaryText
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = model.type,
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText
                    )
                    Text(
                        text = model.startPrice.toString(),
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.secondaryText
                    )
                    Text(
                        text = model.limitPrice.toString(),
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.secondaryText
                    )
                }
                IconButton(
                    modifier = Modifier.padding(start = 10.dp),
                    onClick = { onDeleteLotCard.invoke(model) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .size(36.dp),
                        tint = AppTheme.colors.primaryText
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun Card() {
    MainTheme(darkTheme = false) {
        LotCardItem(model = LotModel(), onDeleteLotCard = {})
    }
}