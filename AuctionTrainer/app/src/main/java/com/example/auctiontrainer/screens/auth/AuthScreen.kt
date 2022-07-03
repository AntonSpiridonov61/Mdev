package com.example.auctiontrainer.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.auctiontrainer.screens.auth.AuthViewModel
import com.example.auctiontrainer.screens.auth.AuthViewState
import com.example.auctiontrainer.screens.auth.views.LoginView
import com.example.auctiontrainer.screens.auth.views.RegistrationView
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.components.LoadingView

@Composable
fun AuthScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val viewState = authViewModel.authViewState.observeAsState(AuthViewState.Login("", ""))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.8f)
        ) {
            when (val state = viewState.value) {
                is AuthViewState.Login -> LoginView(
                    viewState = state,
                    onEmailChanged = { authViewModel.obtainEvent(AuthEvent.LoginEmailChanged(it)) },
                    onPasswordChanged = { authViewModel.obtainEvent(AuthEvent.LoginPasswordChanged(it)) },
                    onLoginClick = { authViewModel.obtainEvent(AuthEvent.Login) }
                )
                is AuthViewState.Registration -> RegistrationView(
                    viewState = state,
                    onNicknameChanged = { authViewModel.obtainEvent(AuthEvent.NicknameChanged(it)) },
                    onEmailChanged = { authViewModel.obtainEvent(AuthEvent.RegEmailChanged(it)) },
                    onPasswordChanged = { authViewModel.obtainEvent(AuthEvent.RegPasswordChanged(it)) },
                    onWhatIsRole = { authViewModel.obtainEvent(AuthEvent.WhatIsRoleChanged(it)) },
                    onRegClick = { authViewModel.obtainEvent(AuthEvent.Registration) }
                )
                is AuthViewState.Loading -> LoadingView()
                is AuthViewState.Success -> {
                    LaunchedEffect("navigation") {
                        navController.navigate(
                            state.route,
                            navOptions = NavOptions.Builder()
                                .setEnterAnim(0)
                                .setExitAnim(0)
                                .build()
                        )
                    }
                }
            }
        }
        Row(

        ) {
            ClickableText(
                modifier = Modifier.padding(
                    horizontal = AppTheme.shapes.padding / 2,
                    vertical = AppTheme.shapes.padding + 8.dp
                ),
                text = AnnotatedString("Вход"),
                style = AppTheme.typography.body,
                onClick = {
                    authViewModel.obtainEvent(AuthEvent.MoveLogin)
                }
            )

            Text(
                modifier = Modifier.padding(
                    vertical = AppTheme.shapes.padding + 8.dp
                ),
                text = "|",
                style = AppTheme.typography.body,
            )

            ClickableText(
                modifier = Modifier.padding(
                    horizontal = AppTheme.shapes.padding / 2,
                    vertical = AppTheme.shapes.padding + 8.dp
                ),
                text = AnnotatedString("Регистрация"),
                style = AppTheme.typography.body,
                onClick = {
                    authViewModel.obtainEvent(AuthEvent.MoveReg)
                }
            )
        }
    }
}