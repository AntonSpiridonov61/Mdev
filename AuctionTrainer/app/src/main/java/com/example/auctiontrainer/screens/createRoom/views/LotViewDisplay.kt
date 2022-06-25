package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.createRoom.CreateRoomViewState
import com.example.auctiontrainer.ui.theme.AppTheme


@Composable
fun LotViewDisplay(
    navController: NavController,
    viewState: CreateRoomViewState.Display,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    ) {
        Column {
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
                                onDeleteLotCard = {  }
                            )
                        }
                    }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.shapes.padding),
            backgroundColor = AppTheme.colors.tintColor,
            onClick = {
                navController.navigate("createLot")
            }) {
            Icon(
                imageVector = Icons.Filled.Create,
                contentDescription = "Settings icon",
                tint = AppTheme.colors.secondaryText
            )
        }
    }
}