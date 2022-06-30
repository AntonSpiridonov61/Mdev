package com.example.auctiontrainer.screens.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.team.views.DisplayView
import com.example.auctiontrainer.screens.team.views.InputCodeView

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
