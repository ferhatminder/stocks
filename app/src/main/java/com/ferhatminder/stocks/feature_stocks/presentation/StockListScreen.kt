package com.ferhatminder.stocks.feature_stocks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.ferhatminder.stocks.feature_stock_prices.presentation.components.StockEditRow
import com.ferhatminder.stocks.feature_stocks.presentation.components.StockList
import com.ferhatminder.stocks.util.TestTags


@Composable
fun StockListScreen(stocksViewModel: StocksViewModel) {
    Column(Modifier.testTag(TestTags.SCREEN_STOCKS)) {
        stocksViewModel.onEvent(StocksViewModel.Event.GetStocks)
        StockEditRow(stocksViewModel, stocksViewModel.state)
        StockList(stocksViewModel, stocksViewModel.state)
    }
}