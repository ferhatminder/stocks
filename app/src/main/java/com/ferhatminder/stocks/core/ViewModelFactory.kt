package com.ferhatminder.stocks.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    stocksViewModelProvider: Provider<StocksViewModel>,
    stockPricesViewModelProvider: Provider<StockPricesViewModel>
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        StocksViewModel::class.java to stocksViewModelProvider,
        StockPricesViewModel::class.java to stockPricesViewModelProvider,
    )

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}