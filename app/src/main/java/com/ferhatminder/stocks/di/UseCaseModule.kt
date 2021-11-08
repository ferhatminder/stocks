package com.ferhatminder.stocks.di

import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UpdateStockPrices
import com.ferhatminder.stocks.feature_stocks.domain.repositories.StockRepository
import com.ferhatminder.stocks.feature_stocks.domain.usecases.GetStocks
import com.ferhatminder.stocks.feature_stocks.domain.usecases.StartEditing
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetStockPricesUseCase(
        stockPricesRepository: StockPricesRepository
    ): GetStockPrices = GetStockPrices(stockPricesRepository)

    @Singleton
    @Provides
    fun provideUnTrackStockPriceUseCase(
        stockPricesRepository: StockPricesRepository
    ): UnTrackStockPrice = UnTrackStockPrice(stockPricesRepository)

    @Singleton
    @Provides
    fun provideUpdateStockPricesUseCase(
        stockPricesRepository: StockPricesRepository
    ): UpdateStockPrices = UpdateStockPrices(stockPricesRepository)

    @Singleton
    @Provides
    fun provideGetStocksUseCase(
        stockRepository: StockRepository,
    ): GetStocks = GetStocks(stockRepository)

    @Singleton
    @Provides
    fun provideStartEditingUseCase(
        stockPricesRepository: StockPricesRepository,
    ): StartEditing = StartEditing(stockPricesRepository)
}