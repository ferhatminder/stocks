package com.ferhatminder.stocks.feature_stock_prices.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatminder.stocks.core.DispatcherProvider
import com.ferhatminder.stocks.feature_stock_prices.domain.entities.StockPrice
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.GetStockPrices
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UnTrackStockPrice
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StockPricesViewModel(
    val getStockPricesUseCase: GetStockPrices,
    val unTrackStockPrice: UnTrackStockPrice,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(State())
    val state: LiveData<State> = _state
    private lateinit var getStockPricesJob: Job

    fun onEvent(event: Event) {
        when (event) {
            is Event.UnTrackStockPriceEvent -> {
                _state.value = State(unTrackStockPrice(event.code))
            }
        }
    }

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            getStockPricesJob = getStockPricesUseCase()
                .onEach {
                    _state.postValue(State(list = it))
                }
                .launchIn(this)
        }
    }

    override fun onCleared() {
        getStockPricesJob.cancel()
        super.onCleared()
    }

    class State(val list: List<StockPrice> = emptyList())

    sealed class Event {
        data class UnTrackStockPriceEvent(val code: String) : Event()
    }
}