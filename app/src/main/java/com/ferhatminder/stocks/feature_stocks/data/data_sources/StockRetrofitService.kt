package com.ferhatminder.stocks.feature_stocks.data.data_sources

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface StockRetrofitService {
    @GET("stocks")
    fun getAllStocks(): Flow<List<String>>
}