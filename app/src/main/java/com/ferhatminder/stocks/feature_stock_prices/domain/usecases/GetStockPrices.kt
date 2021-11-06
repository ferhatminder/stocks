package com.ferhatminder.stocks.feature_stock_prices.domain.usecases

import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import kotlinx.coroutines.flow.Flow

class GetStockPrices(val stockPricesRepository: StockPricesRepository) {
    operator fun invoke(): Flow<List<StockPrice>> {
        return stockPricesRepository.getStockPrices()
    }
}