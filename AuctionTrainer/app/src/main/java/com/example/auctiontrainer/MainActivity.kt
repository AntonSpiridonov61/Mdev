package com.example.auctiontrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auctiontrainer.screens.LoginScreen
import com.example.auctiontrainer.screens.createLot.CreateLotScreen
import com.example.auctiontrainer.screens.createLot.CreateLotViewModel
import com.example.auctiontrainer.screens.createRoom.CreateRoomScreen
import com.example.auctiontrainer.screens.createRoom.CreateRoomViewModel
import com.example.auctiontrainer.screens.organizer.OrganizerMainScreen
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
            val isDarkModeValue = true
            val currentStyle = remember { mutableStateOf(AppStyle.Orange) }
            val currentFontSize = remember { mutableStateOf(AppSize.Big) }
            val currentPaddingSize = remember { mutableStateOf(AppSize.Big) }
            val currentCornersStyle = remember { mutableStateOf(AppCorners.Rounded) }
            val isDarkMode = remember { mutableStateOf(isDarkModeValue) }

            MainTheme(
                style = currentStyle.value,
                darkTheme = isDarkMode.value,
                textSize = currentFontSize.value,
                corners = currentCornersStyle.value,
                paddingSize = currentPaddingSize.value
            ) {
                val navController = rememberNavController()
//                val systemUiController = rememberSystemUiController()
//
//                // Set status bar color
//                SideEffect {
//                    systemUiController.setSystemBarsColor(
//                        color = if (isDarkMode.value) baseDarkPalette.primaryBackground else baseLightPalette.primaryBackground,
//                        darkIcons = !isDarkMode.value
//                    )
//                }

                Surface {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("organizer") {
                            OrganizerMainScreen(navController = navController)
                        }
                        composable("createRoom") {
                            val createRoomViewModel = hiltViewModel<CreateRoomViewModel>()
                            CreateRoomScreen(
                                navController = navController,
                                createRoomViewModel = createRoomViewModel
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
                    }
                }
            }
        }
    }
}