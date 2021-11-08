package com.ferhatminder.stocks.feature_stocks.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel

@Composable
fun StockList(viewModel: StocksViewModel, list: List<Stock>, editing: Boolean) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            list,
            key = { item: Stock -> item.code }
        ) { stock ->
            StockItem(viewModel, item = stock, editing)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}