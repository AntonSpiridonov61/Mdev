package com.example.auctiontrainer.screens.roomTeam.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.auctiontrainer.screens.roomTeam.RoomTeamViewState
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun MakeBetDialog(
    viewState: RoomTeamViewState.DisplayLot,
    onChangedBet: (String) -> Unit,
    onDialogStateChanged: () -> Unit,
    onMakeBetClicked: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDialogStateChanged,
        title = { Text(
            text = "Введите сумму ставки",
            style = AppTheme.typography.caption,
            color = AppTheme.colors.secondaryText
        ) },
        backgroundColor = AppTheme.colors.primaryBackground,
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
                    value = viewState.bet,
                    onValueChange = onChangedBet,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = AppTheme.colors.primaryBackground,
                        textColor = AppTheme.colors.primaryText,
                        focusedIndicatorColor = AppTheme.colors.tintColor,
                        disabledIndicatorColor = AppTheme.colors.controlColor,
                        cursorColor = AppTheme.colors.tintColor
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(.9f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor,
                        disabledBackgroundColor = AppTheme.colors.tintColor.copy(
                            alpha = 0.3f
                        )
                    ),
                    onClick = onMakeBetClicked,
                    enabled = viewState.bet.isNotEmpty()
                ) {
                    Text(
                        text = "Ставка",
                        style = AppTheme.typography.body,
                        color = Color.White
                    )
                }
            }
        }
    )
}