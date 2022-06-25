package com.example.auctiontrainer.screens.team

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.team.views.InputCodeView
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun TeamMainScreen(
    navController: NavController,
    teamViewModel: TeamViewModel
) {
    val teamViewState = teamViewModel.viewState.observeAsState(false)

    if (teamViewState.value == true) {
        InputCodeView(navController = navController, teamViewModel = teamViewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(AppTheme.colors.primaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            modifier = Modifier
                .padding(22.dp)
                .height(48.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AppTheme.colors.tintColor
            ),
            onClick = {
                teamViewModel.onChangeState()
            }

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
            onClick = {

            }
        ) {
            Text(
                text = "Параметры",
                style = AppTheme.typography.body,
                color = AppTheme.colors.primaryText
            )
        }
    }
}
