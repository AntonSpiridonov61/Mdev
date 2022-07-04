package com.example.auctiontrainer.screens.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.auctiontrainer.screens.team.views.DisplayView
import com.example.auctiontrainer.screens.team.views.InputCodeView

@Composable
fun TeamMainScreen(
    navController: NavController,
    teamViewModel: TeamViewModel
) {
    val teamViewState = teamViewModel.teamViewState.observeAsState(TeamViewState.Display())

    DisplayView(
        state = teamViewState.value as TeamViewState.Display,
        onChangedState =  { teamViewModel.obtainEvent(TeamEvent.ChangeState) }
    )

    when (val state = teamViewState.value) {
        is TeamViewState.Dialog -> InputCodeView(
            state = state,
            onCodeChange = { teamViewModel.obtainEvent(TeamEvent.CodeChanged(it)) },
            onChangedState = { teamViewModel.obtainEvent(TeamEvent.ChangeState) },
            onReadyClicked = { teamViewModel.obtainEvent(TeamEvent.ReadyClicked) }
        )
        is TeamViewState.Success -> {
            LaunchedEffect("navigation") {
                navController.navigate(
                    route = "roomTeam",
                    navOptions = NavOptions.Builder()
                        .setEnterAnim(0)
                        .setExitAnim(0)
                        .setPopEnterAnim(0)
                        .setPopExitAnim(0)
                        .build()
                )
            }
        }
        else -> {}
    }



}
