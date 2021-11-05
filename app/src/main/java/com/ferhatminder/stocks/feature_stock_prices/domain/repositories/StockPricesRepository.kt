package com.ferhatminder.stocks.feature_stock_prices.domain.repositories

import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import kotlinx.coroutines.flow.Flow

interface StockPricesRepository {
    fun getStockPrices(): Flow<List<StockPrice>>
}