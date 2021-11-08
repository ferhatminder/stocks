package com.ferhatminder.stocks.feature_stocks.domain.usecases

import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock

class StartEditing(val stockPricesRepository: StockPricesRepository) {
    operator fun invoke(currentStocks: List<Stock>): List<Stock> {
        val trackingStockCodes = stockPricesRepository.getTrackingStockCodes()
        return currentStocks.map {
            Stock(it.code, trackingStockCodes.contains(it.code))
        }
    }
}