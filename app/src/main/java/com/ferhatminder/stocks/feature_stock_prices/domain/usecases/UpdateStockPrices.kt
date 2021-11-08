package com.ferhatminder.stocks.feature_stock_prices.domain.usecases

import com.ferhatminder.stocks.feature_stock_prices.domain.repositories.StockPricesRepository

class UpdateStockPrices(val stockPricesRepository: StockPricesRepository) {
    operator fun invoke(trackingList: List<String>) {
        stockPricesRepository.updateStockPrices(trackingList)
    }
}