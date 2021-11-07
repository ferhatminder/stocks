package com.ferhatminder.stocks.feature_stock_prices.data.repositories

import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.util.Constants
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.random.Random

class StockPricesInMemoryRepositoryImpl(
    var stockPrices: List<StockPrice> = emptyList()
) : StockPricesRepository {

    override fun getStockPrices(): Flow<List<StockPrice>> {
        return flow {
            while (currentCoroutineContext().isActive) {
                emit(stockPrices)
                updateStockPrices()
                delay(Constants.STOCK_PRICE_UPDATE_PERIOD)
            }
        }
    }

    private fun updateStockPrices() {
        stockPrices = stockPrices.map { stockPrice ->
            StockPrice(stockPrice.code, Random.nextDouble(10.0, 20.0))
        }.toList()
    }

    override fun unTrackStockPrice(code: String): List<StockPrice> {
        stockPrices = stockPrices.filter { sp -> sp.code != code }
        return stockPrices
    }
}