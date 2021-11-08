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

    private val stockPrices = listOf(
        StockPrice("GARAN", 9.76),
        StockPrice("THYAO", 13.26)
    )

    @Before
    fun setup() {
        val res: Resources = getInstrumentation().targetContext.resources
        tabs = listOf(
            res.getString(R.string.tab_title_tracked_stocks),
            res.getString(R.string.tab_title_stocks)
        )
    }

    @Test
    fun shouldRenderStockPricePage() {
        shouldRenderTabs()
        shouldRenderInitialStockPrices()
        shouldUnTrackStockPrice()
        shouldRefreshStockPricesPeriodically()
    }

    private fun shouldRenderTabs() {
        composeTestRule.onNodeWithTag(TestTags.SCREEN_TRACKED_STOCKS).assertExists()
        composeTestRule.onNodeWithText(tabs[0]).assertExists()
        composeTestRule.onNodeWithText(tabs[1]).assertExists()
    }

    private fun shouldRenderInitialStockPrices() {
        composeTestRule.runOnUiThread { Thread.sleep(200) }
        composeTestRule.onNodeWithText(stockPrices[0].code).assertIsDisplayed()
        composeTestRule.onNodeWithText(stockPrices[0].price.toString()).assertIsDisplayed()
    }

    private fun shouldUnTrackStockPrice() {
        val unTrackStockPrice = stockPrices[0]
        composeTestRule.onNodeWithText(unTrackStockPrice.code).performGesture { swipeLeft() }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(unTrackStockPrice.code).assertDoesNotExist()
    }

    private fun shouldRefreshStockPricesPeriodically() {
        composeTestRule.runOnUiThread { Thread.sleep(3200) }
        composeTestRule.onNodeWithText(stockPrices[0].price.toString()).assertDoesNotExist()
        composeTestRule.onNodeWithText(stockPrices[1].price.toString()).assertDoesNotExist()
    }

    @Test
    fun shouldRenderStockListPage() {
        shouldNavigateToStocksTab()
        shouldRenderInitialStockEditButtons()
        shouldListStocks()
        shouldRenderStockEditButtonsWhenListed()
    }

    private fun shouldRenderInitialStockEditButtons() {
        composeTestRule.onNodeWithTag(TestTags.EDIT_STOCKS).assertExists()
        composeTestRule.onNodeWithTag(TestTags.CANCEL_EDIT_STOCKS).assertDoesNotExist()
    }

    private fun shouldRenderStockEditButtonsWhenListed() {
        composeTestRule.onNodeWithTag(TestTags.EDIT_STOCKS).assertExists()
        composeTestRule.onNodeWithTag(TestTags.CANCEL_EDIT_STOCKS).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TestTags.SAVE_EDIT_STOCKS).assertDoesNotExist()
    }

    private fun shouldListStocks() {
        composeTestRule.onNodeWithText("AEFES").assertDoesNotExist()
        composeTestRule.runOnUiThread { Thread.sleep(2000) } // network call
        composeTestRule.onNodeWithText("AEFES").assertExists()
        composeTestRule.onNodeWithText("AKSEN").assertExists()
        composeTestRule.onNodeWithText("GARAN").assertExists()
        composeTestRule.onNodeWithText("THYAO").assertExists()
    }

    private fun shouldNavigateToStocksTab() {
        composeTestRule.onNodeWithText(tabs[1]).performClick()
        composeTestRule.onNodeWithTag(TestTags.SCREEN_STOCKS).assertExists()
    }

    private fun shouldNavigateToStockPricesTab() {
        composeTestRule.onNodeWithText(tabs[0]).performClick()
        composeTestRule.onNodeWithTag(TestTags.SCREEN_TRACKED_STOCKS).assertExists()
    }

    @Test
    fun shouldEditTrackedStocks() {
        shouldNavigateToStocksTab()
        composeTestRule.runOnUiThread { Thread.sleep(1300) } // network call
        composeTestRule.onNodeWithTag(TestTags.EDIT_STOCKS).performClick()
        composeTestRule.onNodeWithTag("THYAO_select").assertIsToggleable().assertIsOn()
        composeTestRule.onNodeWithTag("AKSEN_select").assertIsToggleable().assertIsOff()
        composeTestRule.onNodeWithTag(TestTags.CANCEL_EDIT_STOCKS).performClick()

        composeTestRule.onNodeWithTag(TestTags.EDIT_STOCKS).performClick()
        composeTestRule.onNodeWithTag("THYAO_select").assertIsOn()
        composeTestRule.onNodeWithTag("AKSEN_select").assertIsOff()

        composeTestRule.onNodeWithTag("THYAO_select").performGesture { click() }
        composeTestRule.onNodeWithTag("AKSEN_select").performGesture { click() }
        composeTestRule.onNodeWithTag(TestTags.SAVE_EDIT_STOCKS).performClick()

        shouldNavigateToStockPricesTab()
        composeTestRule.runOnUiThread { Thread.sleep(1100) }
        composeTestRule.onNodeWithText("THYAO").assertDoesNotExist()
        composeTestRule.onNodeWithText("AKSEN").assertExists()
    }
}