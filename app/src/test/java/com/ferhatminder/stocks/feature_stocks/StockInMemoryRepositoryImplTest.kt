package com.ferhatminder.stocks.feature_stocks

import com.ferhatminder.stocks.feature_stocks.data.repositories.StockInMemoryRepositoryImpl
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StockInMemoryRepositoryImplTest {

    private val stocks = listOf(
        Stock("AEFES", false),
        Stock("AKSEN", false),
        Stock("GARAN", false),
        Stock("THYAO", false)
    )

    @Test
    fun `should return stocks from memory`() = runBlocking {
        val repo = StockInMemoryRepositoryImpl(stocks)
        Assert.assertEquals(stocks, repo.getStocks().last())
    }
}


