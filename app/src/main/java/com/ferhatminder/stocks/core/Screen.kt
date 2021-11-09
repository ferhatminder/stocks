package com.ferhatminder.stocks.core

sealed class Screen(val route: String) {
    object StockPriceList : Screen(route = "stock_prices")
    object StockList : Screen(route = "stocks")
}