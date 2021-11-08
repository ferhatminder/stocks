package com.ferhatminder.stocks.di

import com.ferhatminder.stocks.feature_stock_prices.data.repositories.StockPricesInMemoryRepositoryImpl
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.feature_stocks.data.repositories.StockInMemoryRepositoryImpl
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.repositories.StockRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStockPricesRepository(): StockPricesRepository = StockPricesInMemoryRepositoryImpl(
        listOf(
            StockPrice("GARAN", 9.76),
            StockPrice("THYAO", 13.26)
        )
    )

    @Singleton
    @Provides
    fun provideStockRepository(): StockRepository = StockInMemoryRepositoryImpl(
        listOf(
            Stock("AEFES", false),
            Stock("AKSEN", false),
            Stock("GARAN", false),
            Stock("THYAO", false)
        )
    )
}