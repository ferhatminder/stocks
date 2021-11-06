package com.ferhatminder.stocks.feature_stock_prices.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice

@Composable
fun StockPriceItem(stockPrice: StockPrice) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stockPrice.code, fontSize = 24.sp)
            stockPrice.price?.let {
                Text(text = it.toString(), fontSize = 24.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StockPricePreview() {
    StockPriceItem(stockPrice = StockPrice("THY", 10.43))
}