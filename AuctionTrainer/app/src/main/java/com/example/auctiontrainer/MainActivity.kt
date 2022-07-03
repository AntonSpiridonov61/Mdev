package com.example.auctiontrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auctiontrainer.screens.auth.AuthScreen
import com.example.auctiontrainer.screens.auth.AuthViewModel
import com.example.auctiontrainer.screens.createLot.CreateLotScreen
import com.example.auctiontrainer.screens.createLot.CreateLotViewModel
import com.example.auctiontrainer.screens.createRoom.CreateRoomScreen
import com.example.auctiontrainer.screens.createRoom.CreateRoomViewModel
import com.example.auctiontrainer.screens.organizer.OrganizerMainScreen
import com.example.auctiontrainer.screens.organizer.OrganizerMainViewModel
import com.example.auctiontrainer.screens.roomOrganizer.RoomOrganizerScreen
import com.example.auctiontrainer.screens.roomOrganizer.RoomOrganizerViewModel
import com.example.auctiontrainer.screens.team.TeamMainScreen
import com.example.auctiontrainer.screens.team.TeamViewModel
import com.example.auctiontrainer.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkModeValue = false
            val currentStyle = remember { mutableStateOf(AppStyle.Green) }
            val isDarkMode = remember { mutableStateOf(isDarkModeValue) }

            MainTheme(
                style = currentStyle.value,
                darkTheme = isDarkMode.value,
            ) {
                val navController = rememberNavController()
                val systemUiController = rememberSystemUiController()

                // Set status bar color
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if (isDarkMode.value) baseDarkPalette.primaryBackground else baseLightPalette.primaryBackground,
                        darkIcons = !isDarkMode.value
                    )
                }

                Surface {
                    NavHost(navController = navController, startDestination = "auth") {
                        composable("auth") {
                            val authViewModel = hiltViewModel<AuthViewModel>()
                            AuthScreen(
                                navController = navController,
                                authViewModel = authViewModel)
                        }
                        composable("organizer") {
                            val organizerViewModel = hiltViewModel<OrganizerMainViewModel>()
                            OrganizerMainScreen(
                                navController = navController,
                                organizerMainViewModel = organizerViewModel
                            )
                        }
                        composable("createRoom") {
                            val createRoomViewModel = hiltViewModel<CreateRoomViewModel>()
                            CreateRoomScreen(
                                navController = navController,
                                createRoomViewModel = createRoomViewModel,
                            )
                        }
                        composable("createLot") {
                            val createLotViewModel = hiltViewModel<CreateLotViewModel>()
                            CreateLotScreen(
                                navController = navController,
                                createViewModel = createLotViewModel
                            )
                        }
                        composable("team") {
                            val teamViewModel = hiltViewModel<TeamViewModel>()
                            TeamMainScreen(
                                navController = navController,
                                teamViewModel = teamViewModel
                            )
                        }
                        composable("roomOrganizer") {
                            val roomViewModel = hiltViewModel<RoomOrganizerViewModel>()
                            RoomOrganizerScreen(
                                navController = navController,
                                roomOrganizerViewModel = roomViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}