package com.example.auctiontrainer.screens.team.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.team.TeamViewState
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun InputCodeView(
    state: TeamViewState.Dialog,
    onChangedState: () -> Unit,
    onCodeChange: (String) -> Unit,
    onReadyClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onChangedState,
        title = { Text(
            text = "Введите код",
            style = AppTheme.typography.caption,
            color = AppTheme.colors.secondaryText
        ) },
        backgroundColor = AppTheme.colors.secondaryBackground,
        buttons = {
            Column(
                modifier = Modifier.padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                    value = state.code,
                    onValueChange = onCodeChange,
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
                        .padding(top = 16.dp)
                        .fillMaxWidth(.9f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor
                    ),
                    onClick = onReadyClicked
                ) {
                    Text(
                        text = "Готово",
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText
                    )
                }
            }
        }
    )
}