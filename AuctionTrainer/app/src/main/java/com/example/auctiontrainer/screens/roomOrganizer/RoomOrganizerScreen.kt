package com.example.auctiontrainer.screens.roomOrganizer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.auctiontrainer.ui.theme.components.LoadingView
import com.example.auctiontrainer.screens.roomOrganizer.views.RoomView


@Composable
fun RoomOrganizerScreen(
    navController: NavController,
    roomOrganizerViewModel: RoomOrganizerViewModel
) {
    val viewSate = roomOrganizerViewModel.roomViewState.observeAsState(RoomViewState.Loading)

    when (val state = viewSate.value) {
        is RoomViewState.Loading -> LoadingView()
        is RoomViewState.MainDisplay -> RoomView(
            state = state,
            onNextLotClick = { }
        )
    }

    LaunchedEffect(key1 = viewSate, block = {
        roomOrganizerViewModel.obtainEvent(event = RoomEvent.EnterScreen)
    })
}