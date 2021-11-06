package com.ferhatminder.stocks.di

import com.ferhatminder.stocks.feature_stock_prices.data.repositories.StockPricesInMemoryRepositoryImpl
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestRepositoryModule {

    @Singleton
    @Provides
    fun provideStockPricesRepository(): StockPricesRepository = StockPricesInMemoryRepositoryImpl(
        listOf(
            StockPrice("GARAN", 9.76),
            StockPrice("THYAO", 13.26)
        )
    )

}