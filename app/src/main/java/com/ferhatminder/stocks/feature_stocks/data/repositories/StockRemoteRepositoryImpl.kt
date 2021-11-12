package com.ferhatminder.stocks.feature_stocks.data.repositories

import com.ferhatminder.stocks.feature_stocks.data.data_sources.StockRetrofitService
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StockRemoteRepositoryImpl(val stockRetrofitService: StockRetrofitService) : StockRepository {
    override fun getStocks(): Flow<List<Stock>> {
        return flow {
            val allStocks = stockRetrofitService
                .getAllStocks()
                .map {
                    Stock(it)
                }
            emit(allStocks)
        }
    }

}