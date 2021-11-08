package com.ferhatminder.stocks.feature_stock_prices.data.data_sources

import com.ferhatminder.stocks.feature_stock_prices.data.models.StockPriceModel
import retrofit2.http.Body
import retrofit2.http.POST

interface StockPriceRetrofitService {

    @POST("stockPrice")
    suspend fun getStockPrices(
        @Body stocks: List<String>
    ): List<StockPriceModel>


}