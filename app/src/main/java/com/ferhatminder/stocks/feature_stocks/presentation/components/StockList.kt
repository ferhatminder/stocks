package com.ferhatminder.stocks.feature_stocks.presentation.components

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel

@Composable
fun StockList(data: LiveData<StocksViewModel.State>) {
    val state by data.observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(state!!.list, key = { item: Stock -> item.code }) { stock ->
            StockItem(item = stock)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}