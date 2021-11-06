package com.ferhatminder.stocks.feature_stock_prices.data.repositories

import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StockPricesInMemoryRepositoryImpl(
    val stockPrices: List<StockPrice> = emptyList()
) : StockPricesRepository {

    override fun getStockPrices(): Flow<List<StockPrice>> {
        return flow {
            emit(
                stockPrices
            )
        }
    }
}