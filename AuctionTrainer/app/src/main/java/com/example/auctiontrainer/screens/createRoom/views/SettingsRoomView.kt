package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.auctiontrainer.ui.theme.components.DropdownItem
import com.example.auctiontrainer.ui.theme.components.DropdownItemModel


@Composable
fun SettingsRoomView(
    onTimeSelected: (String) -> Unit,
    onCntTeamsSelected: (String) -> Unit
) {
    Box() {
        Column() {
            DropdownItem(
                model = DropdownItemModel(
                    title = "Время на лот (в минутах)",
                    values = listOf(
                        "3", "4", "5", "6", "7"
                    )
                ),
                onItemSelected = {
                    val time = when (it) {
                        0 -> "3"
                        1 -> "4"
                        2 -> "5"
                        3 -> "6"
                        4 -> "7"
                        else -> throw NotImplementedError("No valid value $it")
                    }
                    onTimeSelected.invoke(time)
                }
            )
            DropdownItem(
                model = DropdownItemModel(
                    title = "Кол-во команд",
                    values = listOf(
                        "2", "3", "4", "5"
                    )
                ),
                onItemSelected = {
                    val cnt = when (it) {
                        0 -> "2"
                        1 -> "3"
                        2 -> "4"
                        3 -> "5"
                        else -> throw NotImplementedError("No valid value $it")
                    }
                    onCntTeamsSelected.invoke(cnt)
                }
            )
        }
    }
}