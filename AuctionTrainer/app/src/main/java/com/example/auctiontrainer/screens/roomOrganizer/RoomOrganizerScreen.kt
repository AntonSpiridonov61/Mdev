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
    val viewState = roomOrganizerViewModel.roomViewState.observeAsState(RoomViewState.Loading)

    when (val state = viewState.value) {
        is RoomViewState.Loading -> LoadingView()
        is RoomViewState.MainDisplay -> RoomView(
            state = state,
            onArrowCardClick = {
                roomOrganizerViewModel.obtainEvent(RoomEvent.ExpandLot(it))
//                state.lotsExpand[it] = !state.lotsExpand[it]
                               },
            onNextLotClick = { roomOrganizerViewModel.obtainEvent(RoomEvent.NextLotClick) }
        )
    }

    LaunchedEffect(key1 = viewState, block = {
        roomOrganizerViewModel.obtainEvent(event = RoomEvent.EnterScreen)
    })
}