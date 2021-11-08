package com.ferhatminder.stocks.feature_stocks.domain.repositories

import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    fun getStocks(): Flow<List<Stock>>
}