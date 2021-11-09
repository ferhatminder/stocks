package com.ferhatminder.stocks.feature_stocks.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel

@Composable
fun StockList(viewModel: StocksViewModel, data: LiveData<StocksViewModel.State>) {
    val state by data.observeAsState()
    if (state!!.loading) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                state!!.list,
                key = { item: Stock -> item.code }
            ) { stock ->
                StockItem(viewModel, item = stock, state!!.editing)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}