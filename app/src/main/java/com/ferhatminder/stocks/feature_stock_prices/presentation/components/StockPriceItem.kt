package com.ferhatminder.stocks.feature_stock_prices.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.EndToStart
import androidx.compose.material.DismissValue.Default
import androidx.compose.material.DismissValue.DismissedToStart
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StockPriceItem(
    stockPrice: StockPrice,
    onEvent: (event: StockPricesViewModel.Event) -> Unit
) {
    val dismissState = rememberDismissState(
        initialValue = Default,
        confirmStateChange = {
            if (it == DismissedToStart) {
                onEvent(
                    StockPricesViewModel.Event.UnTrackStockPriceEvent(
                        stockPrice.code
                    )
                )
            }
            it == DismissedToStart
        }
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(EndToStart),
        dismissThresholds = { FractionalThreshold(0.4f) },
        background = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissedToStart -> Color.Red
                    else -> Color.Transparent
                }
            )

            val alignment = Alignment.CenterEnd
            val icon = Icons.Default.Delete

            val scale by animateFloatAsState(
                if (dismissState.targetValue == Default) 0.75f else 1f
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    contentDescription = "Untrack Stock",
                    modifier = Modifier.scale(scale)
                )
            }
        },
        dismissContent = {
            Card(
                elevation = animateDpAsState(
                    if (dismissState.dismissDirection != null) 4.dp else 0.dp
                ).value
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stockPrice.code, fontSize = 24.sp)
                    stockPrice.price?.let {
                        Text(text = String.format("%.2f", it), fontSize = 24.sp)
                    } ?: CircularProgressIndicator(modifier = Modifier.scale(0.6f))
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun StockPricePreview() {
    StockPriceItem(
        stockPrice = StockPrice("THYAO", 10.4342)
    ) {}
}