package com.example.auctiontrainer.screens.team.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.auctiontrainer.screens.team.TeamViewModel
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun InputCodeView(
    navController: NavController,
    teamViewModel: TeamViewModel
) {
    AlertDialog(
        onDismissRequest = {
            teamViewModel.onChangeState()
        },
        title = { Text(text = "Введите код") },
        buttons = {
            Column(
                modifier = Modifier.padding(all = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                teamViewModel.code.value?.let {
                    TextField(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        value = it,
                        onValueChange = { teamViewModel.code.value },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = AppTheme.colors.primaryBackground,
                            textColor = AppTheme.colors.primaryText,
                            focusedIndicatorColor = AppTheme.colors.tintColor,
                            disabledIndicatorColor = AppTheme.colors.controlColor,
                            cursorColor = AppTheme.colors.tintColor
                        )
                    )
                }
                Button(
                    onClick = { teamViewModel.onChangeState() }
                ) {
                    Text("Готово")
                }
            }
        }
    )
}