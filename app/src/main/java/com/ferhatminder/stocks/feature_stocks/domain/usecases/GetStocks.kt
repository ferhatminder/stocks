package com.ferhatminder.stocks.feature_stocks.domain.usecases

import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.repositories.StockRepository
import kotlinx.coroutines.flow.Flow

class GetStocks(
    val stockRepository: StockRepository,
) {
    operator fun invoke(): Flow<List<Stock>> {
        return stockRepository.getStocks()
    }
}