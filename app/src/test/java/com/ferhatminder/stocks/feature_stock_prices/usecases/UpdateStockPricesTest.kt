package com.ferhatminder.stocks.feature_stock_prices.usecases

import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UpdateStockPrices
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class UpdateStockPricesTest {

    @Mock
    lateinit var stockPricesRepository: StockPricesRepository

    @Test
    fun `should update stock prices`() = runBlocking {
        doNothing().`when`(stockPricesRepository).updateStockPrices(any())

        val updateStockPrices = UpdateStockPrices(
            stockPricesRepository
        )

        updateStockPrices(listOf("AKSEN", "THYAO"))
        verify(stockPricesRepository, times(1))
            .updateStockPrices(listOf("AKSEN", "THYAO"))
    }
}