package com.example.auctiontrainer.screens.team

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
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
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        Button(onClick = {
            teamViewModel.onChangeState()
        }
        ) {
            Text(text = "Присоединиться")
        }

        Button(onClick = {

        }) {
            Text(text = "Параметры")
        }
    }
}
