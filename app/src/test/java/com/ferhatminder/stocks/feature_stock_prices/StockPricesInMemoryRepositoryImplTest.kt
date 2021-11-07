package com.ferhatminder.stocks.feature_stock_prices

import com.ferhatminder.stocks.feature_stock_prices.data.repositories.StockPricesInMemoryRepositoryImpl
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StockPricesInMemoryRepositoryImplTest {

    @Test
    fun `should get initial stock prices`() = runBlocking {
        val repo = StockPricesInMemoryRepositoryImpl(
            listOf(
                StockPrice("GARAN", 9.76),
                StockPrice("THYAO", 13.26)
            )
        )

        val stockPrices = repo.getStockPrices().last()

        Assert.assertEquals(
            listOf(
                StockPrice("GARAN", 9.76),
                StockPrice("THYAO", 13.26)
            ),
            stockPrices
        )
    }

    @Test
    fun `should untrack stock price`() = run {
        val repo = StockPricesInMemoryRepositoryImpl(
            listOf(
                StockPrice("GARAN", 9.76),
                StockPrice("THYAO", 13.26)
            )
        )

        val stockPrices = repo.unTrackStockPrice("GARAN")

        Assert.assertEquals(
            listOf(
                StockPrice("THYAO", 13.26)
            ),
            stockPrices
        )
    }
}