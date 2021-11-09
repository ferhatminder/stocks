package com.ferhatminder.stocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ferhatminder.stocks.core.Navigation
import com.ferhatminder.stocks.core.Screen
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel
import com.ferhatminder.stocks.ui.theme.StocksTheme

class MainActivity : ComponentActivity() {

    private val stockPricesViewModel: StockPricesViewModel by viewModels {
        appComponent().viewModelsFactory()
    }
    private val stocksViewModel: StocksViewModel by viewModels {
        appComponent().viewModelsFactory()
    }

    private fun appComponent() = (application as StocksApplication).appComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            StocksTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        val navController = rememberNavController()

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
                        Navigation(
                            navController,
                            stockPricesViewModel = stockPricesViewModel, // injection?
                            stocksViewModel = stocksViewModel
                        )
                        if (selectedTabIndex == Tabs.TrackedStocks.index) {
                            navController.navigate(Screen.StockPriceList.route)
                        } else {
                            navController.navigate(Screen.StockList.route)
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

