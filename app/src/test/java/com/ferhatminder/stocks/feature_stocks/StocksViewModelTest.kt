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
import org.mockito.kotlin.*

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
                        Stock("AEFES", false),
                        Stock("AKSEN", false),
                        Stock("GARAN", false),
                        Stock("THYAO", false)
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
                    Stock("AEFES", false),
                    Stock("AKSEN", false),
                    Stock("GARAN", false),
                    Stock("THYAO", false)
                ),
                loadedState.list
            )
            assertEquals(false, loadedState.loading)
        }
    }

    @Test
    fun `should save multiple tracking state of stocks correctly`() =
        coroutineRule.runBlockingTest {
            given(getStocksUseCase.invoke()).willAnswer {
                flow {
                    delay(1000L)
                    emit(
                        listOf(
                            Stock("AEFES", false),
                            Stock("AKSEN", false),
                            Stock("GARAN", true),
                            Stock("THYAO", true)
                        )
                    )
                }
            }

            doNothing().`when`(updateStockPrices).invoke(any())

            val viewModel = StocksViewModel(
                getStocksUseCase,
                startEditing,
                updateStockPrices,
                TestDispatcherProvider(coroutineRule.dispatcher)
            )

            viewModel.state.captureValues {
                advanceTimeBy(1100L) // Network call
                val loadingState = values[0]!!
                assertEquals(
                    emptyList<StockPrice>(),
                    loadingState.list
                )
                assertEquals(true, loadingState.loading)
                assertEquals(false, loadingState.isEditable())

                val loadedState = values[1]!!
                assertEquals(
                    listOf(
                        Stock("AEFES", false),
                        Stock("AKSEN", false),
                        Stock("GARAN", true),
                        Stock("THYAO", true)
                    ),
                    loadedState.list
                )
                assertEquals(false, loadedState.loading)
                assertEquals(true, loadedState.isEditable())

                viewModel.onEvent(StocksViewModel.Event.StartEditing)

                val editingState = values[2]!!
                assertEquals(true, editingState.editing)

                viewModel.onEvent(StocksViewModel.Event.SaveEdit)

                val editedState = values[3]!!
                assertEquals(
                    listOf(
                        Stock("AEFES", false),
                        Stock("AKSEN", true),
                        Stock("GARAN", false),
                        Stock("THYAO", true)
                    ),
                    editedState.list
                )
                assertEquals(false, editedState.loading)
                assertEquals(true, editedState.isEditable())
                assertEquals(false, editedState.editing)
                verify(updateStockPrices, times(1)).invoke(
                    listOf("AKSEN", "THYAO")
                )
        }
    }
}


