package com.ferhatminder.stocks.di

import com.ferhatminder.stocks.feature_stock_prices.data.data_sources.StockPriceRetrofitService
import com.ferhatminder.stocks.feature_stocks.data.data_sources.StockRetrofitService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideStockPriceService(okHttpClient: OkHttpClient): StockPriceRetrofitService {
        return Retrofit.Builder()
            .baseUrl("API")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(StockPriceRetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideStockService(okHttpClient: OkHttpClient): StockRetrofitService {
        return Retrofit.Builder()
            .baseUrl("API")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(StockRetrofitService::class.java)
    }

}