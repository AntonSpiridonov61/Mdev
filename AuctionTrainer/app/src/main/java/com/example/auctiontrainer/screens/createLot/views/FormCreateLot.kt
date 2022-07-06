package com.example.auctiontrainer.screens.createLot.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.screens.createLot.CreateLotViewState
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.components.DropdownItem
import com.example.auctiontrainer.ui.theme.components.DropdownItemModel

@Composable
fun FormCreateLot(
    state: CreateLotViewState.ViewStateInitial,
    onTitleChanged: (String) -> Unit,
    onStartPriceChanged: (String) -> Unit,
    onLimitPriceChanged: (String) -> Unit,
    onTypeSelected: (String) -> Unit,
    onSaveClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    )
    {
        Column(
            modifier = Modifier.background(AppTheme.colors.primaryBackground),
            content = {
                Text(
                    modifier = Modifier.padding(
                        horizontal = AppTheme.shapes.padding,
                        vertical = AppTheme.shapes.padding + 8.dp
                    ),
                    text = "Новый лот",
                    style = AppTheme.typography.heading,
                    color = AppTheme.colors.primaryText
                )
                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    TextField(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        value = state.title,
                        onValueChange = onTitleChanged,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = AppTheme.colors.primaryBackground,
                            textColor = AppTheme.colors.primaryText,
                            focusedIndicatorColor = AppTheme.colors.tintColor,
                            disabledIndicatorColor = AppTheme.colors.controlColor,
                            cursorColor = AppTheme.colors.tintColor
                        ),
                        label = {
                            Text(
                                text = "Название",
                                style = AppTheme.typography.caption,
                                color = AppTheme.colors.secondaryText
                            )
                        }
                    )
                    TextField(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        value = state.startPrice,
                        onValueChange = onStartPriceChanged,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = AppTheme.colors.primaryBackground,
                            textColor = AppTheme.colors.primaryText,
                            focusedIndicatorColor = AppTheme.colors.tintColor,
                            disabledIndicatorColor = AppTheme.colors.controlColor,
                            cursorColor = AppTheme.colors.tintColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = {
                            Text(
                                text = "Начальная цена",
                                style = AppTheme.typography.caption,
                                color = AppTheme.colors.secondaryText
                            )
                        }
                    )
                    TextField(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        value = state.limitPrice,
                        onValueChange = onLimitPriceChanged,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = AppTheme.colors.primaryBackground,
                            textColor = AppTheme.colors.primaryText,
                            focusedIndicatorColor = AppTheme.colors.tintColor,
                            disabledIndicatorColor = AppTheme.colors.controlColor,
                            cursorColor = AppTheme.colors.tintColor
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        label = {
                            Text(
                                text = "Предельная цена",
                                style = AppTheme.typography.caption,
                                color = AppTheme.colors.secondaryText
                            )
                        }
                    )
                }

                DropdownItem(
                    model = DropdownItemModel(
                        title = "Тип лота",
                        values = listOf(
                            "Тип 1",
                            "Тип 2",
                            "Тип 3"
                        )
                    ),
                    onItemSelected = {
                        val type = when (it) {
                            0 -> "Тип 1"
                            1 -> "Тип 2"
                            2 -> "Тип 3"
                            else -> throw NotImplementedError("No valid value for this $it")
                        }
                        onTypeSelected.invoke(type)
                    }
                )

                Button(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    onClick = onSaveClicked,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor
                    ),
                    enabled = state.title.isNotEmpty() &&
                            state.startPrice.isNotEmpty() &&
                            state.limitPrice.isNotEmpty()
                ) {
                    Text(
                        text = "Добавить",
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText
                    )
                }
            }
        )
    }
}