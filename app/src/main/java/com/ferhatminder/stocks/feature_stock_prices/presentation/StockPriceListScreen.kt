package com.ferhatminder.stocks.feature_stock_prices.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.ferhatminder.stocks.feature_stock_prices.presentation.components.StockPriceList
import com.ferhatminder.stocks.util.TestTags

@Composable
fun StockPriceListScreen(stockPricesViewModel: StockPricesViewModel) {
    Column(
        Modifier.testTag(TestTags.SCREEN_TRACKED_STOCKS)
    ) {
        stockPricesViewModel.onEvent(StockPricesViewModel.Event.GetStockPrices)
        StockPriceList(
            stockPricesViewModel.state
        ) { event -> stockPricesViewModel.onEvent(event) }
    }
}