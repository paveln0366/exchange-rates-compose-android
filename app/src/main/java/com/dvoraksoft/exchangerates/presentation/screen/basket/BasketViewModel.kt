package com.dvoraksoft.exchangerates.presentation.screen.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvoraksoft.exchangerates.domain.model.Basket
import com.dvoraksoft.exchangerates.domain.usecase.GetBasketFlowUseCase
import com.dvoraksoft.exchangerates.domain.usecase.UpdateBasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketFlowUseCase: GetBasketFlowUseCase,
    private val updateBasketUseCase: UpdateBasketUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BasketUiState>(BasketUiState.Loading)
    val uiState: StateFlow<BasketUiState> = _uiState.asStateFlow()

    private var observeJob: Job? = null

    init {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        onDateSelected(today)
    }

    fun onDateSelected(date: LocalDate) {
        observeJob?.cancel()
        _uiState.value = BasketUiState.Loading

        observeJob = viewModelScope.launch {
            getBasketFlowUseCase(date)
                .catch { e ->
                    _uiState.value = BasketUiState.Error(e.localizedMessage ?: "Database error")
                }
                .collect { basket ->
                    _uiState.value = BasketUiState.Success(
                        date = date,
                        basket = basket
                    )
                }
        }

        updateBasket(date)
    }

    fun updateBasket(date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = BasketUiState.Loading
            try {
                updateBasketUseCase(date)
            } catch (e: Exception) {
                if (_uiState.value !is BasketUiState.Success) {
                    _uiState.value = BasketUiState.Error(e.localizedMessage ?: "Loading error")
                }
            }
        }
    }
}

sealed interface BasketUiState {

    data object Loading : BasketUiState

    data class Success(
        val date: LocalDate,
        val basket: Basket
    ) : BasketUiState

    data class Error(val message: String) : BasketUiState
}