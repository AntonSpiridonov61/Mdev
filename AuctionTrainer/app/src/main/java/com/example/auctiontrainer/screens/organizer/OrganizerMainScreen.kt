package com.example.auctiontrainer.screens.organizer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.organizer.views.DisplayOrgView
import com.example.auctiontrainer.screens.team.TeamEvent
import com.example.auctiontrainer.ui.theme.components.LoadingView


@Composable
fun OrganizerMainScreen(
    navController: NavController,
    organizerMainViewModel: OrganizerMainViewModel
) {
    val viewState = organizerMainViewModel.organizerMainViewState.observeAsState(
        OrganizerMainViewState.Display("Человек")
    )

    when (val state = viewState.value) {
        is OrganizerMainViewState.Display -> {
            DisplayOrgView(
                state,
                onCreateClick = {
                    navController.navigate("createRoom")
                }
            )
        }
        is OrganizerMainViewState.Loading -> LoadingView()
    }
    LaunchedEffect(key1 = viewState, block = {
        organizerMainViewModel.obtainEvent(event = OrganizerMainEvent.LoadData)
    })
}