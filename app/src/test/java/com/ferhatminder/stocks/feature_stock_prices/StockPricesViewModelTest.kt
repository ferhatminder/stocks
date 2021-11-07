package com.ferhatminder.stocks.feature_stock_prices

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ferhatminder.stocks.core.TestDispatcherProvider
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import com.ferhatminder.stocks.feature_stock_prices.presentation.StockPricesViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import java.util.concurrent.Executors

const val FAKE_NETWORK_DELAY = 1000L

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StockPricesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Mock
    private lateinit var getStockPricesUseCase: GetStockPrices

    @Mock
    private lateinit var unTrackStockPrice: UnTrackStockPrice

    @Test
    fun `should list initial stock prices and update periodically`() = runBlocking {
        given(getStockPricesUseCase.invoke()).willAnswer {
            flow {
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
            TestDispatcherProvider()
        )

        viewModel.state.observeForever {}

        assertEquals(
            emptyList<StockPrice>(),
            viewModel.state.value!!.list
        )

        delay(100L)

        assertEquals(
            listOf(
                StockPrice("GARAN", 9.76),
                StockPrice("THYAO", 13.26)
            ),
            viewModel.state.value!!.list
        )

        delay(3100L)
        assertEquals(
            listOf(
                StockPrice("GARAN", 9.96),
                StockPrice("THYAO", 13.96)
            ),
            viewModel.state.value!!.list
        )
    }

    @Test
    fun `should remove selected stock from tracking list`() = runBlocking {
        given(unTrackStockPrice.invoke(any())).willAnswer {
            listOf(
                StockPrice("THYAO", 13.26)
            )
        }

        val viewModel = StockPricesViewModel(
            getStockPricesUseCase,
            unTrackStockPrice,
            TestDispatcherProvider()
        )

        viewModel.state.observeForever {}

        delay(200L)

        viewModel.onEvent(
            StockPricesViewModel.Event.UnTrackStockPriceEvent("GARAN")
        )

        assertEquals(
            listOf(
                StockPrice("THYAO", 13.26)
            ),
            viewModel.state.value!!.list
        )
    }

}


