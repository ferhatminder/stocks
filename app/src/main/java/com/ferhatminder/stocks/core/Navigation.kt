package com.ferhatminder.stocks.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPriceListScreen
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
import com.ferhatminder.stocks.feature_stocks.presentation.StockListScreen
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    stockPricesViewModel: StockPricesViewModel,
    stocksViewModel: StocksViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.StockPriceList.route
    ) {
        composable(route = Screen.StockPriceList.route) {
            StockPriceListScreen(stockPricesViewModel)
        }
        composable(route = Screen.StockList.route) {
            StockListScreen(stocksViewModel)
        }
    }
}


