package com.example.auctiontrainer.screens.auth.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.auth.AuthViewState
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun RegistrationView(
    viewState: AuthViewState.Registration,
    onNicknameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onWhatIsRole: (String) -> Unit,
    onRegClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground),
        content = {
            Column(modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .padding(
                        vertical = AppTheme.shapes.padding + 8.dp
                    ),
                    text = "Регистрация",
                    style = AppTheme.typography.heading,
                    color = AppTheme.colors.primaryText
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(.8f),
                    singleLine = true,
                    value = viewState.nickname,
                    onValueChange = onNicknameChanged,
//                    isError = viewState.nickname.isEmpty(),
                    label = { Text(
                        modifier = Modifier.padding(bottom = 18.dp),
                        text = "Ник",
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
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
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
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .padding(top = 22.dp)
                ) {
                    listOf("Организатор", "Команда").forEach { item ->
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                            ) {
                            RadioButton(
                                selected = (viewState.whatIsRole == item),
                                onClick = { onWhatIsRole.invoke(item) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = AppTheme.colors.tintColor,
                                    unselectedColor = AppTheme.colors.secondaryText
                                )
                            )
                            ClickableText(
                                modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
                                text = AnnotatedString(item),
                                style = TextStyle(
                                    color = AppTheme.colors.primaryText,
                                ),
                                onClick = { onWhatIsRole.invoke(item) }
                            )
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 40.dp, start = 16.dp, end = 16.dp)
                        .height(48.dp)
                        .fillMaxWidth(.7f),
                    onClick = onRegClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor,
                        disabledBackgroundColor = AppTheme.colors.tintColor.copy(
                            alpha = 0.3f
                        )
                    ),
                    enabled = viewState.email.isNotEmpty() && viewState.password.isNotEmpty() && viewState.nickname.isNotEmpty()
                ) {
                    if (viewState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Зарегистрироваться",
                            style = AppTheme.typography.body,
                            color = Color.White
                        )
                    }
                }
            }
        }
    )
}