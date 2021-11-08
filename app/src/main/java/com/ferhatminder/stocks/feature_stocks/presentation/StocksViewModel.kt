package com.ferhatminder.stocks.feature_stocks.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.usecases.GetStocks
import com.ferhatminder.stocks.utils.DispatcherProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StocksViewModel(
    val getStocks: GetStocks,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(State())
    val state: LiveData<State> = _state

    class State(
        val loading: Boolean = false,
        val list: List<Stock> = emptyList()
    )

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            _state.postValue(State(true))
            getStocks()
                .onEach {
                    _state.postValue(State(list = it))
                }.launchIn(this)
        }
    }

}