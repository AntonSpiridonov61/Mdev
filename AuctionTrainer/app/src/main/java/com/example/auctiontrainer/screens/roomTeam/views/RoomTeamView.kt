package com.example.auctiontrainer.screens.roomTeam.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
        Box(
            modifier = Modifier.height(100.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .padding(
                        vertical = AppTheme.shapes.padding
                    ),
                text = viewState.lot.title,
                style = AppTheme.typography.heading,
                color = AppTheme.colors.primaryText,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .padding(
                        vertical = AppTheme.shapes.padding
                    ),
                text = viewState.lot.type,
                style = AppTheme.typography.toolbar,
                color = AppTheme.colors.primaryText,
                textAlign = TextAlign.Right
            )
            Text(
                modifier = Modifier
                    .padding(
                        vertical = AppTheme.shapes.padding
                    ),
                text = viewState.lot.price.toString(),
                style = AppTheme.typography.body,
                color = AppTheme.colors.primaryText,
                textAlign = TextAlign.Justify
            )
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp)
                .fillMaxWidth()
                .align(Alignment.End),
            onClick = onDialogStateChanged,
            enabled = viewState.isMakeBet,
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