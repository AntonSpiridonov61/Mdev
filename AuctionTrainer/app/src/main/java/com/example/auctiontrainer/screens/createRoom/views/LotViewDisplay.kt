package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auctiontrainer.base.AppData
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.screens.createRoom.CreateRoomViewState
import com.example.auctiontrainer.ui.theme.AppTheme


@Composable
fun LotViewDisplay(
    navController: NavController,
    viewState: CreateRoomViewState.Display,
    onStartClick: () -> Unit,
    onDeleteLotCard: (LotModel) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(.8f)
        ) {
            Text(
                modifier = Modifier.padding(
                    start = AppTheme.shapes.padding,
                    end = AppTheme.shapes.padding,
                    top = AppTheme.shapes.padding + 8.dp
                ),
                text = "Список лотов",
                style = AppTheme.typography.heading,
                color = AppTheme.colors.primaryText
            )

            LazyColumn {
                viewState.items
                    .forEach { cardItem ->
                        item {
                            LotCardItem(
                                model = cardItem,
                                onDeleteLotCard = { onDeleteLotCard.invoke(cardItem) }
                            )
                        }
                    }
            }
        }
        Column(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(AppTheme.shapes.padding)
                    .align(Alignment.CenterHorizontally),
                backgroundColor = AppTheme.colors.tintColor,
                onClick = {
                    navController.navigate("createLot")
                }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Settings icon",
                        tint = AppTheme.colors.primaryText
                    )
                    Text(
                        text = "Добавить",
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText
                    )
                }
            }
            Button(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 22.dp, end = 22.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.tintColor
                ),
                onClick = onStartClick
            ) {
                Text(
                    text = "Начать",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
            }
        }
    }
}