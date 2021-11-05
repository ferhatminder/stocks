package com.ferhatminder.stocks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.filters.LargeTest
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import org.junit.Rule
import org.junit.Test

@LargeTest
class End2EndTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun e2eTest() {
        shouldListStockPrices()
    }

    private fun shouldListStockPrices() {
        val stockPrices = listOf(
            StockPrice("GARAN", 9.76),
            StockPrice("THYAO", 13.26)
        )
        for (stockPrice in stockPrices) {
            composeTestRule.onNodeWithText(stockPrice.code).assertIsDisplayed()
            composeTestRule.onNodeWithText(stockPrice.price.toString()).assertIsDisplayed()
        }
    }

}