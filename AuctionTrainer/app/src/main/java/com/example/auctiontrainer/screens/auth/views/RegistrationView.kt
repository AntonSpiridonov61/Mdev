package com.example.auctiontrainer.screens.auth.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.auth.AuthEvent
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
            Text(
                modifier = Modifier.padding(
                    horizontal = AppTheme.shapes.padding + 4.dp,
                    vertical = AppTheme.shapes.padding + 8.dp
                ),
                text = "Регистрация",
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
                                style = AppTheme.typography.body,
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
                        backgroundColor = AppTheme.colors.tintColor
                    )
                ) {
                    Text(
                        text = "Зарегистрироваться",
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText
                    )
                }
            }
        }
    )
}