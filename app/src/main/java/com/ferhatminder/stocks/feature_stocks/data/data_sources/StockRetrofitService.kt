package com.ferhatminder.stocks.feature_stocks.data.data_sources

import retrofit2.http.GET

interface StockRetrofitService {
    @GET("stocks")
    suspend fun getAllStocks(): List<String>
}