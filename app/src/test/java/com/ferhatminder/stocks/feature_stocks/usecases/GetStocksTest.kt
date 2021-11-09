package com.ferhatminder.stocks.feature_stocks.usecases

import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.repositories.StockRepository
import com.ferhatminder.stocks.feature_stocks.domain.usecases.GetStocks
import kotlinx.coroutines.delay
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
class GetStocksTest {

    @Mock
    lateinit var stockRepository: StockRepository

    @Test
    fun `should get all stocks successfully`() = runBlocking {
        given(stockRepository.getStocks()).willAnswer {
            flow {
                delay(1000L)
                emit(
                    listOf(
                        Stock("AEFES"),
                        Stock("AKSEN"),
                    )
                )
            }
        }

        val getStocks = GetStocks(stockRepository)
        val stocks = getStocks().last()

        Assert.assertEquals(
            listOf(
                Stock("AEFES"),
                Stock("AKSEN"),
            ),
            stocks
        )
    }

}