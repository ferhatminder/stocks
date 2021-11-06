package com.ferhatminder.stocks.feature_stocks.usecases

import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class UnTrackStockPriceTest {

    @Mock
    lateinit var stockPricesRepository: StockPricesRepository

    @Test
    fun `should untrack initial stock prices`() = runBlocking {
        given(stockPricesRepository.unTrackStockPrice(any())).willAnswer {
            listOf(
                StockPrice("THYAO", 13.26)
            )
        }

        val unTrackStockPrice = UnTrackStockPrice(
            stockPricesRepository
        )
        val stockPrices = unTrackStockPrice("GARAN")

        Assert.assertEquals(
            listOf(
                StockPrice("THYAO", 13.26),
            ),
            stockPrices
        )
    }
}