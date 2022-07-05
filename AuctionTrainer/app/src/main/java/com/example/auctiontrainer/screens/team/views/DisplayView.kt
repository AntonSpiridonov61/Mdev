package com.example.auctiontrainer.screens.team.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.team.TeamViewState
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.components.GreetingView

@Composable
fun DisplayView(
    viewState: TeamViewState.Display,
    onDialogStateChanged: () -> Unit,
    onCodeChanged: (String) -> Unit,
    onReadyClicked: () -> Unit
) {
    if (viewState.dialogState) {
        InputCodeView(
            viewState = viewState,
            onDialogStateChanged = onDialogStateChanged,
            onCodeChanged = onCodeChanged,
            onReadyClicked = onReadyClicked
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(AppTheme.colors.primaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        GreetingView(viewState.nickname)
        Column {
            Button(
                modifier = Modifier
                    .padding(22.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.tintColor
                ),
                onClick = onDialogStateChanged
            ) {
                Text(
                    text = "Присоединится",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
            }
            Button(
                modifier = Modifier
                    .padding(bottom = 24.dp, start = 22.dp, end = 22.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.tintColor
                ),
                onClick = { }
            ) {
                Text(
                    text = "Параметры",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
            }
        }
    }
}