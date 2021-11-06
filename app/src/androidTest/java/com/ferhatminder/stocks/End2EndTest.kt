package com.ferhatminder.stocks

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performGesture
import androidx.compose.ui.test.swipeLeft
import androidx.test.filters.LargeTest
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import org.junit.Rule
import org.junit.Test

@ExperimentalMaterialApi
@LargeTest
class End2EndTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun e2eTest() {
        shouldListStockPrices()
        shouldUnTrackStockPrice()
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

    private fun shouldUnTrackStockPrice() {
        val unTrackStockPrice = StockPrice("GARAN", 9.76)
        composeTestRule.onNodeWithText(unTrackStockPrice.code).performGesture { swipeLeft() }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(unTrackStockPrice.code).assertDoesNotExist()
    }

}