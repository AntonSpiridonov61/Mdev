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
import com.example.auctiontrainer.screens.createLot.CreateLotEvent
import com.example.auctiontrainer.screens.team.views.DisplayView
import com.example.auctiontrainer.screens.team.views.InputCodeView
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun TeamMainScreen(
    navController: NavController,
    teamViewModel: TeamViewModel
) {
    val teamViewState = teamViewModel.teamViewState.observeAsState(TeamViewState.ViewStateDisplay)

    DisplayView(
        state = teamViewState.value,
        onChangedState =  { teamViewModel.obtainEvent(TeamEvent.ChangeState) }
    )

    when (val state = teamViewState.value) {
        is TeamViewState.ViewStateDialog -> InputCodeView(
            state = state,
            onCodeChange = { teamViewModel.obtainEvent(TeamEvent.CodeChanged(it)) },
            onChangedState = { teamViewModel.obtainEvent(TeamEvent.ChangeState) },
            onReadyClicked = { teamViewModel.obtainEvent(TeamEvent.ReadyClicked) }
        )
    }



}
