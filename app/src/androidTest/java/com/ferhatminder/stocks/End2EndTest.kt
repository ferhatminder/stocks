package com.ferhatminder.stocks

import android.content.res.Resources
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
class End2EndTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var tabs: List<String>

    @Before
    fun setup() {
        val res: Resources = getInstrumentation().targetContext.resources
        tabs = listOf(
            res.getString(R.string.tab_title_tracked_stocks),
            res.getString(R.string.tab_title_stocks)
        )
    }

    @Test
    fun e2eTest() {
        shouldRenderTabs()
        shouldListStockPrices()
        shouldUnTrackStockPrice()
        shouldNavigateToStocksTab()
    }

    private fun shouldNavigateToStocksTab() {
        composeTestRule.onNodeWithText(tabs[1]).performClick()
        composeTestRule.onNodeWithTag(TestTags.SCREEN_STOCKS).assertExists()
    }

    private fun shouldRenderTabs() {
        composeTestRule.onNodeWithTag(TestTags.SCREEN_TRACKED_STOCKS).assertExists()
        composeTestRule.onNodeWithText(tabs[0]).assertExists()
        composeTestRule.onNodeWithText(tabs[1]).assertExists()
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