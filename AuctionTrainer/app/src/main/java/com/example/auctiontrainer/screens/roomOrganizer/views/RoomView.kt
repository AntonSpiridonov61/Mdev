package com.example.auctiontrainer.screens.roomOrganizer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.LotState
import com.example.auctiontrainer.base.SettingsRoom
import com.example.auctiontrainer.screens.roomOrganizer.RoomViewState
import com.example.auctiontrainer.ui.theme.MainTheme
import com.example.auctiontrainer.ui.theme.*


@Composable
fun RoomView(
    state: RoomViewState.MainDisplay,
    onNextLotClick: () -> Unit
) {
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
                    text = "Код доступа: ${state.code[0]}",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.primaryText
                )
                Text(
                    modifier = Modifier
                        .padding(
                            horizontal = AppTheme.shapes.padding,
                            vertical = AppTheme.shapes.padding + 8.dp
                        ),
                    text = "0/${state.settings[0].cntTeams}",
                    style = AppTheme.typography.caption,
                    color = AppTheme.colors.primaryText,
                    textAlign = TextAlign.Center,

                    )

            }
            LazyColumn(
                modifier = Modifier.fillMaxHeight(.85f)
            ) {
                state.lots.forEach {
                    item {
                        LotItem(
                            name = it.title,
                            state = it.state
                        )
                    }
                }
            }
            Button(
                modifier = Modifier
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    .height(48.dp)
                    .fillMaxWidth()
                    .align(Alignment.End),
                onClick = onNextLotClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.tintColor
                )
            ) {
                Text(
                    text = "Начать лот",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun PreView() {
//    MainTheme(darkTheme = true) {
//        RoomView(
//            state = RoomViewState.MainDisplay(listOf(
//                LotModel("qwe", "rty", 90),
//                LotModel("qwe1", "rty2", 90),
//                LotModel("qwe3", "rty3", 69),
//            ),listOf("12345"), SettingsRoom()
//            ),
//            onNextLotClick = { }
//        )
//    }
//}