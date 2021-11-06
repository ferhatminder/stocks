package com.ferhatminder.stocks.feature_stock_prices.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel

@Composable
fun StockPriceList(data: LiveData<StockPricesViewModel.State>) {
    val state by data.observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(state!!.list) { stockPrice ->
            StockPriceItem(
                stockPrice = stockPrice
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StockPriceListPreview() {
    val data = MutableLiveData(
        StockPricesViewModel.State(
            listOf(
                StockPrice("GARAN", 9.76),
                StockPrice("THYAO", 13.26)
            )
        )
    )
    StockPriceList(data)
}