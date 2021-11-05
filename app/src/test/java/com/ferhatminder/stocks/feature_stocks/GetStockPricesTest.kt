package com.ferhatminder.stocks.feature_stocks

import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class GetStockPricesTest {

    @Mock
    lateinit var stockPricesRepository: StockPricesRepository

    @Test
    fun `should get initial stock prices`() = runBlocking {
        given(stockPricesRepository.getStockPrices()).willAnswer {
            flow {
                emit(
                    listOf(
                        StockPrice("GARAN", 9.76),
                        StockPrice("THYAO", 13.26)
                    )
                )
            }
        }

        val getStockPrices = GetStockPrices(
            stockPricesRepository
        )
        val stockPrices = getStockPrices()

        Assert.assertEquals(
            listOf(
                StockPrice("GARAN", 9.76),
                StockPrice("THYAO", 13.26)
            ),
            stockPrices.last()
        )
    }
}