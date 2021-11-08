package com.ferhatminder.stocks.feature_stock_prices

import com.ferhatminder.stocks.feature_stock_prices.data.repositories.StockPricesInMemoryRepositoryImpl
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StockPricesInMemoryRepositoryImplTest {

    private val initialStockPrices: List<StockPrice> = listOf(
        StockPrice("GARAN", 9.76),
        StockPrice("THYAO", 13.26)
    )

    @Test
    fun `should get stock prices periodically`() = runBlocking {
        val repo = StockPricesInMemoryRepositoryImpl(initialStockPrices)
        repo.getStockPrices().take(2).collectIndexed { index, stockPrices ->
            if (index == 0) {
                Assert.assertEquals(initialStockPrices, stockPrices)
            } else if (index == 1) {
                Assert.assertNotEquals(initialStockPrices, stockPrices)
            }
        }
    }

    @Test
    fun `should untrack stock price`() = run {
        val repo = StockPricesInMemoryRepositoryImpl(initialStockPrices)

        val stockPrices = repo.unTrackStockPrice("GARAN")

        Assert.assertEquals(
            listOf(
                StockPrice("THYAO", 13.26)
            ),
            stockPrices
        )
    }

    @Test
    fun `should update stock prices`() = run {
        val repo = StockPricesInMemoryRepositoryImpl(initialStockPrices)

        repo.updateStockPrices(listOf("AKSEN", "THYAO"))

        Assert.assertEquals(
            listOf(
                StockPrice("AKSEN", null),
                StockPrice("THYAO", 13.26),
            ),
            repo.stockPrices
        )
    }

    @Test
    fun `should get current tracked stock price codes`() = run {
        val repo = StockPricesInMemoryRepositoryImpl(initialStockPrices)
        repo.getTrackingStockCodes()
        Assert.assertEquals(initialStockPrices.map { it.code }, repo.getTrackingStockCodes())
    }


}