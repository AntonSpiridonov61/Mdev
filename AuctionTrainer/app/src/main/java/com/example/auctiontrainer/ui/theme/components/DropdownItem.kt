package com.example.auctiontrainer.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.auctiontrainer.R
import com.example.auctiontrainer.ui.theme.AppTheme

data class DropdownItemModel(
    val title: String,
    val currentIndex: Int = 0,
    val values: List<String>
)

@Composable
fun DropdownItem(
    model: DropdownItemModel,
    onItemSelected: ((Int) -> Unit)? = null
) {
    val isDropdownOpen = remember { mutableStateOf(false) }
    val currentPosition = remember { mutableStateOf(model.currentIndex) }

    Column {
        Box(
            modifier = Modifier
                .background(AppTheme.colors.primaryBackground)
                .fillMaxWidth()
        ) {
            Row(
                Modifier
                    .clickable {
                        isDropdownOpen.value = true
                    }
                    .padding(AppTheme.shapes.padding)
                    .background(AppTheme.colors.primaryBackground),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = AppTheme.shapes.padding),
                    text = model.title,
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.primaryText
                )

                Text(
                    text = model.values[currentPosition.value],
                    style = AppTheme.typography.body,
                    color = AppTheme.colors.secondaryText
                )

                Icon(
                    modifier = Modifier
                        .padding(start = AppTheme.shapes.padding / 4)
                        .size(18.dp)
                        .align(Alignment.CenterVertically),
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                    contentDescription = "Arrow",
                    tint = AppTheme.colors.secondaryText
                )
            }

            Divider(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.BottomStart),
                thickness = 0.5.dp,
                color = AppTheme.colors.secondaryText.copy(
                    alpha = 0.3f
                )
            )
        }

        DropdownMenu(
            expanded = isDropdownOpen.value,
            onDismissRequest = {
                isDropdownOpen.value = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(AppTheme.colors.secondaryBackground)
        ) {
            model.values.forEachIndexed { index, value ->
                DropdownMenuItem(onClick = {
                    currentPosition.value = index
                    isDropdownOpen.value = false
                    onItemSelected?.invoke(index)
                }) {
                    Text(
                        text = value,
                        style = AppTheme.typography.body,
                        color = AppTheme.colors.primaryText
                    )
                }
            }
        }
    }
}

//@Composable
//@Preview
//fun MenuItem_Preview() {
//    AppTheme(
//        darkTheme = true
//    ) {
//        DropdownItem(
//            model = DropdownItemModel(
//                title = "Type",
//                values = listOf(
//                    "type 1",
//                    "type 1",
//                    "type 1"
//                )
//            )
//        )
//    }
//}