package com.ferhatminder.stocks.feature_stocks.usecases

import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.usecases.StartEditing
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@RunWith(MockitoJUnitRunner::class)
class StartEditingTest {

    @Mock
    lateinit var stockPricesRepository: StockPricesRepository

    @Test
    fun `should get all stocks successfully`() = runBlocking {
        given(stockPricesRepository.getTrackingStockCodes()).willAnswer {
            listOf("THYAO")
        }

        val getStocks = StartEditing(stockPricesRepository)
        val stocks = getStocks(listOf(Stock("THYAO"), Stock("AEFES")))

        Assert.assertEquals(
            listOf(
                Stock("THYAO", true),
                Stock("AEFES"),
            ),
            stocks
        )
    }

}