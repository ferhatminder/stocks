package com.ferhatminder.stocks.feature_stock_prices

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
import com.ferhatminder.stocks.utils.CoroutineScopeRule
import com.ferhatminder.stocks.utils.TestDispatcherProvider
import com.ferhatminder.stocks.utils.captureValues
import com.ferhatminder.stocks.utils.getValueForTest
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
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StockPricesViewModelTest {

    @get:Rule
    var coroutineRule = CoroutineScopeRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getStockPricesUseCase: GetStockPrices

    @Mock
    private lateinit var unTrackStockPrice: UnTrackStockPrice

    @Test
    fun `should list initial stock prices and update periodically`() =
        coroutineRule.runBlockingTest {
            given(getStockPricesUseCase.invoke()).willAnswer {
                flow {
                    delay(100)
                    emit(
                        listOf(
                            StockPrice("GARAN", 9.76),
                            StockPrice("THYAO", 13.26)
                        )
                    )
                    delay(3000L)
                    emit(
                        listOf(
                            StockPrice("GARAN", 9.96),
                            StockPrice("THYAO", 13.96)
                        )
                    )
                }
            }

            val viewModel = StockPricesViewModel(
                getStockPricesUseCase,
                unTrackStockPrice,
                TestDispatcherProvider(coroutineRule.dispatcher)
            )

            viewModel.state.captureValues {
                advanceTimeBy(3200L)
                assertEquals(
                    emptyList<StockPrice>(),
                    values[0]!!.list
                )

                assertEquals(
                    listOf(
                        StockPrice("GARAN", 9.76),
                        StockPrice("THYAO", 13.26)
                    ),
                    values[1]!!.list
                )
                assertEquals(
                    listOf(
                        StockPrice("GARAN", 9.96),
                        StockPrice("THYAO", 13.96)
                    ),
                    values[2]!!.list
                )

            }

        }

    @Test
    fun `should remove selected stock from tracking list`() = coroutineRule.runBlockingTest {
        given(getStockPricesUseCase.invoke()).willAnswer {
            flow {
                delay(100L)
                emit(
                    listOf(
                        StockPrice("GARAN", 9.76),
                        StockPrice("THYAO", 13.26)
                    )
                )
            }
        }

        given(unTrackStockPrice.invoke(any())).willAnswer {
            listOf(
                StockPrice("THYAO", 13.26)
            )
        }

        val viewModel = StockPricesViewModel(
            getStockPricesUseCase,
            unTrackStockPrice,
            TestDispatcherProvider(coroutineRule.dispatcher)
        )

        advanceTimeBy(200L)
        viewModel.onEvent(
            StockPricesViewModel.Event.UnTrackStockPriceEvent("GARAN")
        )

        assertEquals(
            listOf(
                StockPrice("THYAO", 13.26)
            ),
            viewModel.state.getValueForTest()!!.list
        )
    }

}


