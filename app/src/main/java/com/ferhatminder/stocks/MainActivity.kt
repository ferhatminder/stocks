package com.ferhatminder.stocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.ferhatminder.stocks.core.DispatcherProvider
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
import com.ferhatminder.stocks.feature_stock_prices.presentation.components.StockPriceList
import com.ferhatminder.stocks.ui.theme.StocksTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getStockPrices: GetStockPrices

    @Inject
    lateinit var unTrackStockPrice: UnTrackStockPrice

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as StocksApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val viewModel = StockPricesViewModel(
            getStockPrices,
            unTrackStockPrice,
            dispatcherProvider
        )

        setContent {
            StocksTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    StockPriceList(viewModel.state)
                }
            }
        }
    }
}

