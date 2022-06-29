package com.example.auctiontrainer.screens.createRoom


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.auctiontrainer.screens.createRoom.views.LotViewDisplay
import com.example.auctiontrainer.screens.createRoom.views.LotViewNotItems
import com.example.auctiontrainer.screens.createRoom.views.SettingsRoomView

@Composable
fun CreateRoomScreen(
    navController: NavController,
    createRoomViewModel: CreateRoomViewModel,
) {
    val viewState = createRoomViewModel.createRoomViewState.observeAsState(CreateRoomViewState.NoItems)

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        SettingsRoomView(
            onTimeSelected = { createRoomViewModel.obtainEvent(CreateRoomEvent.TimeSelected(it)) },
            onCntTeamsSelected = { createRoomViewModel.obtainEvent(CreateRoomEvent.CntTeamSelected(it)) }
        )

        when (val state = viewState.value) {
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
    }

    LaunchedEffect(key1 = viewState, block = {
        createRoomViewModel.obtainEvent(event = CreateRoomEvent.EnterScreen)
    })
}