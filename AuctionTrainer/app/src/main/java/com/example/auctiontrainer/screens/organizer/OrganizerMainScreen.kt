package com.example.auctiontrainer.screens.organizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.organizer.views.DisplayOrgView
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun OrganizerMainScreen(
    navController: NavController,
    organizerMainViewModel: OrganizerMainViewModel
) {
    val viewState = organizerMainViewModel.organizerMainViewState.observeAsState(
        OrganizerMainViewState.Display("Человек")
    )

    DisplayOrgView(
        viewState.value as OrganizerMainViewState.Display,
        onCreateClick = {
            navController.navigate("createRoom")
        }
    )
}