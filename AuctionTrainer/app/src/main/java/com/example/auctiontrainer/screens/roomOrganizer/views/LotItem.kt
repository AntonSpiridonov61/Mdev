package com.example.auctiontrainer.screens.roomOrganizer.views

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.R
import com.example.auctiontrainer.base.LotModel
import com.example.auctiontrainer.base.LotState
import com.example.auctiontrainer.ui.theme.AppTheme
import com.example.auctiontrainer.ui.theme.MainTheme

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun LotItem(
    lot: LotModel,
    expanded: Boolean,
    onArrowCardClick: () -> Unit
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")

    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = 200)
    }, label = "rotationDegreeTransition") {
        if (expanded) -90f else 90f
    }


    Card(
        modifier = Modifier
            .padding(
                horizontal = AppTheme.shapes.padding,
                vertical = AppTheme.shapes.padding / 2
            )
            .fillMaxWidth(),
        elevation = 3.dp,
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
//                modifier = Modifier.fillMaxWidth(.7f),
                    text = lot.title,
                    style = AppTheme.typography.toolbar,
                    color = AppTheme.colors.primaryText
                )

                Text(
                    textAlign = TextAlign.Right,
                    text = lot.state,
                    style = AppTheme.typography.toolbar,
                    color = AppTheme.colors.secondaryText
                )

                IconButton(
                    onClick = onArrowCardClick,
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                            contentDescription = "Expandable Arrow",
                            modifier = Modifier
                                .rotate(arrowRotationDegree)
                                .size(16.dp),
                            tint = AppTheme.colors.primaryText
                        )
                    }
                )
            }
            ExpandableContent(lot, expanded)
        }

    }
}

@Composable
fun ExpandableContent(
    lot: LotModel,
    visible: Boolean = true,
) {
    AnimatedVisibility(
        visible = visible
    ) {
        Column {
            Text(
                text = lot.price.toString(),
                textAlign = TextAlign.Center
            )
            Text(
                text = lot.type,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PrewLot() {
    MainTheme() {
        LotItem(lot = LotModel("qqqq", "wwww", 1000), expanded = true, onArrowCardClick = {})
    }
}