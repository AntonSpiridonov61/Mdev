package com.example.auctiontrainer.screens.roomOrganizer.views

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.R
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.LotState
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.MainTheme

@Composable
fun LotItem(
    lot: LotModel,
    bets: List<Map<String, Int>>,
    expanded: Boolean,
    onArrowCardClick: () -> Unit
) {
    val expandedState = remember { mutableStateOf(expanded) }


    Card(
        modifier = Modifier
            .padding(
                horizontal = AppTheme.shapes.padding,
                vertical = AppTheme.shapes.padding / 2
            )
            .shadow(
                elevation = 8.dp
            )
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = AppTheme.colors.primaryBackground,
//        backgroundColor = when (lot.state) {
//            "Текущий" -> AppTheme.colors.currentLot
//            "Завершен" -> AppTheme.colors.completedLot
//            else -> AppTheme.colors.primaryBackground
//        },
        shape = AppTheme.shapes.cornersStyle
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(AppTheme.shapes.padding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lot.title,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )

                Text(
                    text = lot.state,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.secondaryText
                )

                IconButton(
                    onClick = { expandedState.value = !expandedState.value },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                            contentDescription = "Expandable Arrow",
                            modifier = Modifier
                                .rotate(if (expanded) -90f else 90f),
                            tint = AppTheme.colors.primaryText
                        )
                    }
                )
            }
            ExpandableContent(lot, bets, expandedState.value)
        }

    }
}

@Composable
fun ExpandableContent(
    lot: LotModel,
    bets: List<Map<String, Int>>,
    visible: Boolean = true,
) {
    AnimatedVisibility(
        visible = visible
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.shapes.padding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Тип: ${lot.type}",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
                Text(
                    text = "Начальная цена: ${lot.startPrice}",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
                Text(
                    text = "Предельная цена: ${lot.limitPrice}",
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )
            }
            Column(
                modifier = Modifier.padding(top = 26.dp)
            ) {
                bets.forEach {
                    it.entries.forEach {
                        Divider(
                            thickness = 0.5.dp,
                            color = AppTheme.colors.secondaryText.copy(
                                alpha = 0.3f
                            )
                        )
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp)
                        ) {
                            Text(
                                text = it.key,
                                style = AppTheme.typography.body,
                                color = AppTheme.colors.primaryText
                            )
                            Text(
                                text = " - ${it.value}",
                                style = AppTheme.typography.body,
                                color = AppTheme.colors.primaryText
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PrewLot() {
    MainTheme() {
        LotItem(lot = LotModel("qqqq", "wwww", 1000),
            expanded = true,
            bets = listOf(mapOf("qwer" to 200, "asddf" to 2100, "vhjkhg" to 300)),
            onArrowCardClick = {})
    }
}