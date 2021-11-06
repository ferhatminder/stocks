package com.ferhatminder.stocks.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
    override val io: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
    override val default: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
    override val unconfined: CoroutineDispatcher
        get() = TestCoroutineDispatcher()
}


/*
class StockPriceFakeDataSource {
    suspend fun getRandomPricesForStockCodes(codes: List<String>): List<StockPrice> {
        delay(800L) // Fake Network Call
        return codes.map { code ->
            StockPrice(code, Random.nextDouble(10.0, 20.0))
        }.toList()
    }
}

class StockPriceCacheDataSource {
    var stockPriceMap: SortedMap<String, StockPrice> = sortedMapOf()

    fun getStockPriceList(): List<StockPrice> {
        return stockPriceMap.map { map -> map.value }.toList()
    }

    fun getTrackedStockCodes(): List<String> {
        return stockPriceMap.keys.toList()
    }

    fun stopTrackingStock(code: String) {
        stockPriceMap.remove(code)
    }

    fun startOrStopTrackingStocks(codes: List<String>) {
        codes.forEach {
            stockPriceMap.putIfAbsent(it, StockPrice(it, null))
        }
        stockPriceMap = stockPriceMap.filter { !codes.contains(it.key) }.toSortedMap()
    }

    fun refreshStockPrices(stockPrices: List<StockPrice>) {
        stockPriceMap.putAll(stockPrices.map { it.code to it }.toMap())
    }
}

data class Stock(val code: String, val isTracking: Boolean = false)

class StockPriceFakeRepositoryImpl(
    val cache: StockPriceCacheDataSource,
    val dataSource: StockPriceFakeDataSource
) : StockPriceRepository {

    override suspend fun getStockPrices(): Flow<List<StockPrice>> {
        return flow {
            // add initial stocks for testing
            val stockPrice1 = StockPrice("GARAN", 9.76)
            val stockPrice2 = StockPrice("THYAO", 13.26)
            cache.stockPriceMap = sortedMapOf(
                Pair(stockPrice1.code, stockPrice1),
                Pair(stockPrice2.code, stockPrice2)
            )
            emit(cache.getStockPriceList())

            while (true) {
                val stockPrices =
                    dataSource.getRandomPricesForStockCodes(cache.getTrackedStockCodes())
                cache.refreshStockPrices(stockPrices)
                emit(cache.getStockPriceList())
                delay(3 * 1000L) // wait 3 sec
            }
        }
    }

}*/