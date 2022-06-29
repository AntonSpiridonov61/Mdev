package com.example.auctiontrainer.screens.createLot.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.base.DropdownItem
import com.example.auctiontrainer.base.DropdownItemModel
import com.example.auctiontrainer.screens.createLot.CreateLotViewState
import com.example.auctiontrainer.ui.theme.AppTheme

@Composable
fun FormCreateLot(
    state: CreateLotViewState.ViewStateInitial,
    onTitleChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
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
                    Text(
                        text = "Название",
                        style = AppTheme.typography.caption,
                        color = AppTheme.colors.secondaryText
                    )

                    TextField(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        value = state.lotTitle,
                        onValueChange = onTitleChanged,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = AppTheme.colors.primaryBackground,
                            textColor = AppTheme.colors.primaryText,
                            focusedIndicatorColor = AppTheme.colors.tintColor,
                            disabledIndicatorColor = AppTheme.colors.controlColor,
                            cursorColor = AppTheme.colors.tintColor
                        )
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

                Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                    Text(
                        text = "Цена",
                        style = AppTheme.typography.caption,
                        color = AppTheme.colors.secondaryText
                    )

                    TextField(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        singleLine = true,
                        value = state.lotPrice.toString(),
                        onValueChange = onPriceChanged,
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
                    modifier = Modifier
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    onClick = onSaveClicked,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor,
                        disabledBackgroundColor = AppTheme.colors.tintColor.copy(
                            alpha = 0.3f
                        )
                    )
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