package com.example.auctiontrainer.screens.organizer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.organizer.OrganizerMainViewState
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.MainTheme
import com.example.auctiontrainer.ui.theme.components.GreetingView

@Composable
fun DisplayOrgView(
    viewState: OrganizerMainViewState.Display,
    onCreateClick: () -> Unit
) {
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
                    .padding(bottom = 24.dp, start = 22.dp, end = 22.dp)
                    .height(48.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.tintColor
                ),
                onClick = onCreateClick
            ) {
                Text(
                    text = "Создать комнату",
                    style = AppTheme.typography.body,
                    color = Color.White
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
                onClick = {

                }
            ) {
                Text(
                    text = "Параметры",
                    style = AppTheme.typography.body,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMain() {
    MainTheme {
        DisplayOrgView(
            viewState = OrganizerMainViewState.Display("qwerty"),
            onCreateClick = { }
        )
    }
}