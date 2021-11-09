package com.ferhatminder.stocks.feature_stocks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UpdateStockPrices
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.usecases.GetStocks
import com.ferhatminder.stocks.feature_stocks.domain.usecases.StartEditing
import com.ferhatminder.stocks.feature_stocks.presentation.StocksViewModel
import com.ferhatminder.stocks.utils.CoroutineScopeRule
import com.ferhatminder.stocks.utils.TestDispatcherProvider
import com.ferhatminder.stocks.utils.captureValues
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StocksViewModelTest {

    @get:Rule
    var coroutineRule = CoroutineScopeRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getStocksUseCase: GetStocks

    @Mock
    private lateinit var updateStockPrices: UpdateStockPrices

    @Mock
    private lateinit var startEditing: StartEditing

    @Test
    fun `should list stocks after fetching successfully`() = coroutineRule.runBlockingTest {
        given(getStocksUseCase.invoke()).willAnswer {
            flow {
                delay(1000L)
                emit(
                    listOf(
                        Stock("AEFES"),
                        Stock("AKSEN"),
                        Stock("THYAO")
                    )
                )
            }
        }
        val viewModel = StocksViewModel(
            getStocksUseCase,
            startEditing,
            updateStockPrices,
            TestDispatcherProvider(coroutineRule.dispatcher)
        )

        viewModel.onEvent(StocksViewModel.Event.GetStocks)
        viewModel.state.captureValues {
            advanceTimeBy(1100L) // Network call
            val loadingState = values[0]!!
            assertEquals(
                emptyList<StockPrice>(),
                loadingState.list
            )
            assertEquals(true, loadingState.loading)

            val loadedState = values[1]!!
            assertEquals(
                listOf(
                    Stock("AEFES"),
                    Stock("AKSEN"),
                    Stock("THYAO"),
                ),
                loadedState.list
            )
            assertEquals(false, loadedState.loading)
        }
    }

}


