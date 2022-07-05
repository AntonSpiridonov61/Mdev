package com.example.auctiontrainer.screens.roomTeam

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.roomOrganizer.RoomEvent
import com.example.auctiontrainer.screens.roomTeam.views.RoomTeamView

@Composable
fun RoomTeamScreen(
    navController: NavController,
    teamViewModel: RoomTeamViewModel
) {
    val viewState = teamViewModel.roomTeamViewState.observeAsState()
    when (val state = viewState.value) {
        is RoomTeamViewState.DisplayLot -> RoomTeamView(
            viewState = state,
            onChangedBet = { teamViewModel.obtainEvent(RoomTeamEvent.BetChanged(it)) },
            onDialogStateChanged = { teamViewModel.obtainEvent(RoomTeamEvent.ChangeDialogState) },
            onMakeBetClicked = { teamViewModel.obtainEvent(RoomTeamEvent.MakeBetClicked) }
        )
        else -> {}
    }

    LaunchedEffect(key1 = Unit, block = {
        teamViewModel.obtainEvent(event = RoomTeamEvent.LoadData)
    })
}