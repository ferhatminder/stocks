package com.ferhatminder.stocks.feature_stock_prices.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import com.ferhatminder.stocks.utils.DispatcherProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class StockPricesViewModel @Inject constructor(
    val getStockPricesUseCase: GetStockPrices,
    val unTrackStockPrice: UnTrackStockPrice,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(State())
    val state: LiveData<State> = _state
    private var getStockPricesJob: Job? = null

    fun onEvent(event: Event) {
        when (event) {
            is Event.UnTrackStockPriceEvent -> {
                _state.value = State(unTrackStockPrice(event.code))
            }
            Event.GetStockPrices -> {
                if (getStockPricesJob == null || !getStockPricesJob!!.isActive) {
                    viewModelScope.launch(dispatcherProvider.io) {
                        getStockPricesJob = getStockPricesUseCase()
                            .onEach {
                                _state.postValue(State(list = it))
                            }.launchIn(this)
                    }
                }
            }
        }
    }

    class State(val list: List<StockPrice> = emptyList())

    sealed class Event {
        data class UnTrackStockPriceEvent(val code: String) : Event()
        object GetStockPrices : Event()
    }
}