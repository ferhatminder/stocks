package com.ferhatminder.stocks.feature_stocks.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferhatminder.stocks.feature_stock_prices.domain.usecases.UpdateStockPrices
import com.ferhatminder.stocks.feature_stocks.domain.entities.Stock
import com.ferhatminder.stocks.feature_stocks.domain.usecases.GetStocks
import com.ferhatminder.stocks.feature_stocks.domain.usecases.StartEditing
import com.ferhatminder.stocks.utils.DispatcherProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StocksViewModel(
    val getStocks: GetStocks,
    val startEditing: StartEditing,
    val updateStockPrices: UpdateStockPrices,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _state: MutableLiveData<State> = MutableLiveData(State())
    val state: LiveData<State> = _state

    private lateinit var beforeEdit: List<Stock>

    class State(
        val loading: Boolean = false,
        val list: List<Stock> = emptyList(),
        val editing: Boolean = false
    ) {
        fun isEditable(): Boolean {
            return !loading && list.isNotEmpty()
        }
    }

    init {
        viewModelScope.launch(dispatcherProvider.io) {
            _state.postValue(State(true))
            getStocks()
                .onEach {
                    _state.postValue(State(list = it))
                }.launchIn(this)
        }
    }

    sealed class Event {
        object StartEditing : Event()
        object CancelEdit : Event()
        object SaveEdit : Event()
        data class OnCheckChange(val checked: Boolean, val item: Stock) : Event()
    }

    fun onEvent(event: Event) {
        val currentState = _state.value!!
        when (event) {
            Event.StartEditing -> {
                beforeEdit = currentState.list
                val stocks = startEditing(beforeEdit)
                _state.value = State(list = stocks, editing = true)
            }
            Event.SaveEdit -> {
                val editedList = currentState.list
                updateStockPrices(editedList.filter { it.tracking }.map { it.code })
                _state.value = State(list = editedList)
            }
            Event.CancelEdit -> {
                _state.value = State(list = beforeEdit)
            }
            is Event.OnCheckChange -> {
                _state.value = State(
                    list = currentState.list.map {
                        if (event.item == it) {
                            Stock(it.code, event.checked)
                        } else {
                            it
                        }
                    },
                    editing = true
                )
            }
        }
    }
}