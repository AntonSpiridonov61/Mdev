package com.example.auctiontrainer.screens.roomTeam.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.roomTeam.RoomTeamViewState
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun RoomTeamView(
    viewState: RoomTeamViewState.DisplayLot,
    onChangedBet: (String) -> Unit,
    onDialogStateChanged: () -> Unit,
    onMakeBetClicked: () -> Unit
) {
    if (viewState.dialogState) {
        MakeBetDialog(
            viewState = viewState,
            onChangedBet = onChangedBet,
            onDialogStateChanged = onDialogStateChanged,
            onMakeBetClicked = onMakeBetClicked
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        LazyRow() {
            itemsIndexed(viewState.lots) { id, lot ->
                LotItem(
                    lot = lot,
                    currentLot = viewState.currentLot - 1,
                    idLot = id
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (viewState.currentLot) {
                0 -> {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = AppTheme.shapes.padding
                            ),
                        text = "Ожидайте начало",
                        style = AppTheme.typography.heading,
                        color = AppTheme.colors.primaryText,
                        textAlign = TextAlign.Center
                    )
                }
                viewState.lots.size + 1 -> {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = AppTheme.shapes.padding
                            ),
                        text = "На этом всё",
                        style = AppTheme.typography.heading,
                        color = AppTheme.colors.primaryText,
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = AppTheme.shapes.padding
                            ),
                        text = viewState.lots[viewState.currentLot - 1].title,
                        style = AppTheme.typography.heading,
                        color = AppTheme.colors.primaryText,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = AppTheme.shapes.padding
                            ),
                        text = "Тип: " + viewState.lots[viewState.currentLot - 1].type,
                        style = AppTheme.typography.toolbar,
                        color = AppTheme.colors.primaryText,
                        textAlign = TextAlign.Right
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = AppTheme.shapes.padding
                            ),
                        text = "Стартовая цена: " + viewState.lots[viewState.currentLot - 1].startPrice.toString(),
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText,
                        textAlign = TextAlign.Justify
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = AppTheme.shapes.padding
                            ),
                        text = "Предельная цена: " + viewState.lots[viewState.currentLot - 1].limitPrice.toString(),
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .fillMaxWidth()
                .align(Alignment.End),
            onClick = onDialogStateChanged,
            enabled = viewState.connectedTeams[viewState.nickname] == true,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppTheme.colors.tintColor
            )
        ) {
            Text(
                text = "Сделать ставку",
                style = AppTheme.typography.body,
                color = AppTheme.colors.primaryText
            )
        }
    }
}