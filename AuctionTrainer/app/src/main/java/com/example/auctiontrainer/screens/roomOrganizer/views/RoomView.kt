package com.example.auctiontrainer.screens.roomOrganizer.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.LotState
import com.example.auctiontrainer.base.SettingsRoom
import com.example.auctiontrainer.screens.roomOrganizer.RoomViewState
import com.example.auctiontrainer.ui.theme.MainTheme
import com.example.auctiontrainer.ui.theme.*


@SuppressLint("MutableCollectionMutableState")
@Composable
fun RoomView(
    state: RoomViewState.MainDisplay,
    onArrowCardClick: (Int) -> Unit,
    onExodusClick: () -> Unit,
    onNextLotClick: () -> Unit
) {
    val isExpanded = remember { mutableStateOf(state.lotsExpand) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    ){
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.shapes.padding,
                            vertical = AppTheme.shapes.padding + 8.dp
                        )
                        .fillMaxWidth(.84f),
                    text = "Код доступа: ${state.code}",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.primaryText
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.shapes.padding,
                            vertical = AppTheme.shapes.padding + 8.dp
                        ),
                    text = "${state.connectedTeams.size}/${state.settings.cntTeams}",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.primaryText,
                    textAlign = TextAlign.Center,

                    )

            }
            LazyColumn(
                modifier = Modifier.fillMaxHeight(.8f)
            ) {
                itemsIndexed(state.lots) { id, lot ->
                    Log.d("bets", state.allBets.toString())
                    LotItem(
                        lot = lot,
                        expanded = isExpanded.value[id],
                        bets = state.allBets[lot.title] ?: mapOf<String, Int>(),
                        winner = state.winners[lot.title] ?: "",
                        onArrowCardClick = { onArrowCardClick.invoke(id) }
                    )
                }
            }
            Column(
//                modifier = Modifier.align(Alignment.End)
            ) {
                Button(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    onClick = onExodusClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor,
                        disabledBackgroundColor = AppTheme.colors.tintColor.copy(
                            alpha = 0.3f
                        )
                    )
                ) {
                    Text(
                        text = "Исход раунда",
                        style = AppTheme.typography.body,
                        color = Color.White
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 20.dp)
                        .height(48.dp)
                        .fillMaxWidth(),
                    onClick = onNextLotClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.tintColor,
                        disabledBackgroundColor = AppTheme.colors.tintColor.copy(
                            alpha = 0.3f
                        )
                    )
                ) {
                    Text(
                        text = "Начать лот",
                        style = AppTheme.typography.body,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreView() {
    MainTheme(darkTheme = false) {
        RoomView(
            state = RoomViewState.MainDisplay(listOf(
                    LotModel("qwe", "rty", 90),
                    LotModel("qwe1", "rty2", 90),
                    LotModel("qwe3", "rty3", 69),
                    LotModel("qwe3", "rty3", 69),
                ),
                currentLot = 1,
                settings = SettingsRoom(4, 3),
                connectedTeams = mapOf("team" to true),
                code = "te362",
                lotsExpand = mutableListOf(true, false, true, true),
                winners = mapOf("qwe" to "team"),
                allBets = mapOf("qwe" to mapOf("team" to 28, "sp" to 34)),
            ),
            onExodusClick = {},
            onNextLotClick = {},
            onArrowCardClick = {}
        )
    }
}