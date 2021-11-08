package com.ferhatminder.stocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UpdateStockPrices
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
import com.ferhatminder.stocks.feature_stock_prices.presentation.components.StockEditRow
import com.ferhatminder.stocks.feature_stock_prices.presentation.components.StockPriceList
import com.ferhatminder.stocks.feature_stocks.domain.usecases.GetStocks
import com.ferhatminder.stocks.feature_stocks.domain.usecases.StartEditing
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel
import com.ferhatminder.stocks.feature_stocks.presentation.components.StockList
import com.ferhatminder.stocks.ui.theme.StocksTheme
import com.ferhatminder.stocks.util.TestTags
import com.ferhatminder.stocks.utils.DispatcherProvider
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var getStockPrices: GetStockPrices

    @Inject
    lateinit var unTrackStockPrice: UnTrackStockPrice

    @Inject
    lateinit var updateStockPrices: UpdateStockPrices

    @Inject
    lateinit var getStocks: GetStocks

    @Inject
    lateinit var startEditing: StartEditing

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as StocksApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        val stockPricesViewModel = StockPricesViewModel(
            getStockPrices,
            unTrackStockPrice,
            dispatcherProvider
        )

        val stocksViewModel = StocksViewModel(
            getStocks,
            startEditing,
            updateStockPrices,
            dispatcherProvider
        )

        setContent {
            StocksTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        var selectedTabIndex by remember { mutableStateOf(Tabs.TrackedStocks.index) }
                        TabRow(selectedTabIndex = selectedTabIndex) {
                            Tabs.list().forEach { item ->
                                TabColumn(
                                    title = LocalContext.current.getString(item.titleID),
                                    onClick = { selectedTabIndex = item.index },
                                    selected = (selectedTabIndex == item.index)
                                )
                            }
                        }

                        if (selectedTabIndex == Tabs.TrackedStocks.index) {
                            Column(
                                Modifier.testTag(TestTags.SCREEN_TRACKED_STOCKS)
                            ) {
                                StockPriceList(
                                    stockPricesViewModel.state
                                ) { event -> stockPricesViewModel.onEvent(event) }
                            }
                        } else {
                            Column(Modifier.testTag(TestTags.SCREEN_STOCKS)) {
                                val state by stocksViewModel.state.observeAsState()
                                StockEditRow(stocksViewModel, state!!)
                                StockList(stocksViewModel, state!!.list, state!!.editing)
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class Tabs(val index: Int, val titleID: Int) {
        object TrackedStocks : Tabs(index = 0, titleID = R.string.tab_title_tracked_stocks)
        object Stocks : Tabs(index = 1, titleID = R.string.tab_title_stocks)

        companion object {
            fun list() = listOf(TrackedStocks, Stocks)
        }
    }
}


@Composable
fun TabColumn(title: String, onClick: () -> Unit, selected: Boolean) {
    Tab(selected, onClick) {
        Column(
            Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

