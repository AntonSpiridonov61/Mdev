package com.example.auctiontrainer.screens.createRoom

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.auctiontrainer.screens.createRoom.views.LoadingView
import com.example.auctiontrainer.screens.createRoom.views.LotViewDisplay
import com.example.auctiontrainer.screens.createRoom.views.LotViewNotItems

@Composable
fun CreateRoomScreen(
    navController: NavController,
    createRoomViewModel: CreateRoomViewModel
) {
    val viewState = createRoomViewModel.createRoomViewState.observeAsState()

    Log.d("State", viewState.value.toString())
    when (val state = viewState.value) {
        CreateRoomViewState.Loading -> LoadingView()
        CreateRoomViewState.NoItems -> LotViewNotItems(
            onCreateClick = {
                navController.navigate(
                    route = "createLot",
                    navOptions = NavOptions.Builder()
                        .setEnterAnim(0)
                        .setExitAnim(0)
                        .setPopEnterAnim(0)
                        .setPopExitAnim(0)
                        .build()
                )
            }
        )
        is CreateRoomViewState.Display -> LotViewDisplay(
            navController = navController,
            viewState = state,
        )
    }

    LaunchedEffect(key1 = viewState, block = {
        createRoomViewModel.obtainEvent(event = CreateRoomEvent.EnterScreen)
    })
}