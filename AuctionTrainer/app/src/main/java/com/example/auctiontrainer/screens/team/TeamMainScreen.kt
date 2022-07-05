package com.example.auctiontrainer.screens.team

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.auctiontrainer.screens.roomOrganizer.RoomEvent
import com.example.auctiontrainer.screens.team.views.DisplayView
import com.example.auctiontrainer.screens.team.views.InputCodeView
import com.example.auctiontrainer.ui.theme.components.LoadingView

@Composable
fun TeamMainScreen(
    navController: NavController,
    teamViewModel: TeamViewModel
) {
    val teamViewState = teamViewModel.teamViewState.observeAsState(TeamViewState.Loading)

    when (val state = teamViewState.value) {
        is TeamViewState.Display -> DisplayView(
            viewState = state,
            onCodeChanged = { teamViewModel.obtainEvent(TeamEvent.CodeChanged(it)) },
            onDialogStateChanged = { teamViewModel.obtainEvent(TeamEvent.ChangeDialogState) },
            onReadyClicked = { teamViewModel.obtainEvent(TeamEvent.ReadyClicked) }
        )
        is TeamViewState.Loading -> LoadingView()
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
    }
    LaunchedEffect(key1 = teamViewState, block = {
        teamViewModel.obtainEvent(event = TeamEvent.LoadData)
    })
}
