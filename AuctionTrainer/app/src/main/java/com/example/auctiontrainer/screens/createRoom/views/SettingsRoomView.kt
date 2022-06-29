package com.example.auctiontrainer.screens.createRoom.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.auctiontrainer.base.DropdownItem
import com.example.auctiontrainer.base.DropdownItemModel


@Composable
fun SettingsRoomView(
    onTimeSelected: (String) -> Unit,
    onCntTeamsSelected: (String) -> Unit
) {
    Box() {
        Column() {
            DropdownItem(
                model = DropdownItemModel(
                    title = "Время на лот",
                    values = listOf(
                        "3 Минуты",
                        "4 минуты",
                        "5 Минут",
                        "6 Минут",
                        "7 Минут",
                    )
                ),
                onItemSelected = {
                    val time = when (it) {
                        0 -> "3 Минуты"
                        1 -> "4 Минуты"
                        2 -> "5 Минут"
                        3 -> "6 Минут"
                        4 -> "7 Минут"
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