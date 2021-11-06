package com.ferhatminder.stocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ferhatminder.stocks.core.DispatcherProvider
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
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
                    val state by viewModel.state.observeAsState()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(state!!.list) { stockPrice ->
                            StockPriceItem(
                                stockPrice = stockPrice
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StockPriceItem(stockPrice: StockPrice) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stockPrice.code, fontSize = 24.sp)
        stockPrice.price?.let {
            Text(text = it.toString(), fontSize = 24.sp)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun StockPricePreview() {
    StocksTheme {
        StockPriceItem(stockPrice = StockPrice("THY", 10.43))
    }
}