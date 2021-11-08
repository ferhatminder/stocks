package com.ferhatminder.stocks.feature_stocks.data.repositories

import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.repositories.StockRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StockInMemoryRepositoryImpl(private val stocks: List<Stock>) : StockRepository {
    override fun getStocks(): Flow<List<Stock>> {
        return flow {
            delay(1000L)
            emit(stocks)
        }
    }

}