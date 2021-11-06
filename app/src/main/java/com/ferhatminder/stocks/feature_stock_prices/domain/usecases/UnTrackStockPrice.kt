package com.ferhatminder.stocks.feature_stock_prices.domain.usecases

import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository

class UnTrackStockPrice(val stockPricesRepository: StockPricesRepository) {
    operator fun invoke(code: String): List<StockPrice> {
        return stockPricesRepository.unTrackStockPrice(code)
    }
}