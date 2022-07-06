package com.example.auctiontrainer.screens.createLot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.createLot.views.FormCreateLot

@Composable
fun CreateLotScreen(
    navController: NavController,
    createViewModel: CreateLotViewModel,
) {
    val viewState = createViewModel.createLotViewState.observeAsState(CreateLotViewState.ViewStateInitial())

    when (val state = viewState.value) {
        is CreateLotViewState.ViewStateInitial -> FormCreateLot(
            state = state,
            onTitleChanged = { createViewModel.obtainEvent(CreateLotEvent.TitleChanged(it)) },
            onStartPriceChanged = { createViewModel.obtainEvent(CreateLotEvent.StartPriceChanged(it)) },
            onLimitPriceChanged = { createViewModel.obtainEvent(CreateLotEvent.LimitPriceChanged(it)) },
            onTypeSelected = { createViewModel.obtainEvent(CreateLotEvent.TypeSelected(it)) },
            onSaveClicked = { createViewModel.obtainEvent(CreateLotEvent.SaveClicked) }
        )

        is CreateLotViewState.ViewStateSuccess -> {
            navController.popBackStack(route = "createLot", inclusive = true)
        }
    }
}