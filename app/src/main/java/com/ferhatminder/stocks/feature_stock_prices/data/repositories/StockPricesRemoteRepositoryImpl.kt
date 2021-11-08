package com.ferhatminder.stocks.feature_stock_prices.data.repositories

import com.ferhatminder.stocks.feature_stock_prices.data.data_sources.StockPriceRetrofitService
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.util.Constants
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive

class StockPricesRemoteRepositoryImpl(val remote: StockPriceRetrofitService) :
    StockPricesRepository {
    var stockPrices: List<StockPrice> = emptyList()

    override fun getStockPrices(): Flow<List<StockPrice>> {
        return flow {
            while (currentCoroutineContext().isActive) {
                emit(stockPrices)
                fetchStockPrices()
                delay(Constants.STOCK_PRICE_UPDATE_PERIOD)
            }
        }
    }

    private suspend fun fetchStockPrices() {
        setStockPricesSorted(
            remote.getStockPrices(stockPrices.map { it.code })
                .map {
                    StockPrice(
                        it.code, it.price
                    )
                })
    }

    override fun unTrackStockPrice(code: String): List<StockPrice> {
        setStockPricesSorted(stockPrices.filter { sp -> sp.code != code })
        return stockPrices
    }

    override fun getTrackingStockCodes(): List<String> {
        return stockPrices.map { it.code }
    }

    override fun updateStockPrices(codes: List<String>) {
        val filtered = stockPrices.filter {
            codes.contains(it.code)
        }

        val newAdded = codes.filter { code ->
            filtered.find { it.code == code } == null
        }.map {
            StockPrice(it, null)
        }

        setStockPricesSorted(filtered + newAdded)
    }

    private fun setStockPricesSorted(newStockPrices: List<StockPrice>) {
        stockPrices = newStockPrices.sortedBy { it.code }
    }
}