package com.example.auctiontrainer.screens.auth.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.auth.AuthEvent
import com.example.auctiontrainer.screens.auth.AuthViewState
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.MainTheme
import com.example.auctiontrainer.ui.theme.components.DropdownItem
import com.example.auctiontrainer.ui.theme.components.DropdownItemModel


@Composable
fun LoginView(
    viewState: AuthViewState.Login,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground),
        content = {
            Text(
                modifier = Modifier.padding(
                    horizontal = AppTheme.shapes.padding + 4.dp,
                    vertical = AppTheme.shapes.padding + 8.dp
                ),
                text = "Вход",
                style = AppTheme.typography.heading,
                color = AppTheme.colors.primaryText
            )
            Column(modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(.8f),
                    singleLine = true,
                    value = viewState.email,
                    onValueChange = onEmailChanged,
//                    isError = viewState.email.isEmpty(),
                    label = { Text(
                        modifier = Modifier.padding(bottom = 18.dp),
                        text = "Email",
                        style = AppTheme.typography.caption,
                        color = AppTheme.colors.secondaryText
                    ) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = AppTheme.colors.primaryBackground,
                        textColor = AppTheme.colors.primaryText,
                        focusedIndicatorColor = AppTheme.colors.tintColor,
                        disabledIndicatorColor = AppTheme.colors.controlColor,
                        cursorColor = AppTheme.colors.tintColor
                    )
                )

                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 22.dp)
                        .fillMaxWidth(.8f),
                    singleLine = true,
                    value = viewState.password,
                    onValueChange = onPasswordChanged,
//                    isError = viewState.password.isEmpty(),
                    label = { Text(
                        modifier = Modifier.padding(bottom = 18.dp),
                        text = "Пароль",
                        style = AppTheme.typography.caption,
                        color = AppTheme.colors.secondaryText
                    ) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = AppTheme.colors.primaryBackground,
                        textColor = AppTheme.colors.primaryText,
                        focusedIndicatorColor = AppTheme.colors.tintColor,
                        disabledIndicatorColor = AppTheme.colors.controlColor,
                        cursorColor = AppTheme.colors.tintColor
                    )
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 40.dp, start = 16.dp, end = 16.dp)
                        .height(48.dp)
                        .fillMaxWidth(.5f),
                    onClick = onLoginClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor
                    )
                ) {
                    Text(
                        text = "Войти",
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PrevLogin() {
    MainTheme() {
        LoginView(viewState = AuthViewState.Login("qwe", "asd"),
            onEmailChanged = {}, onPasswordChanged = {}, onLoginClick = {})
    }
}