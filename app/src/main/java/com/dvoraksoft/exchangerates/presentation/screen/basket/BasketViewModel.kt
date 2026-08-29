package com.dvoraksoft.exchangerates.presentation.screen.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvoraksoft.exchangerates.domain.entity.Basket
import com.dvoraksoft.exchangerates.domain.usecase.GetBasketFlowUseCase
import com.dvoraksoft.exchangerates.domain.usecase.RefreshBasketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject
import kotlin.time.Clock

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketFlowUseCase: GetBasketFlowUseCase,
    private val refreshBasketUseCase: RefreshBasketUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BasketUiState>(BasketUiState.Loading)
    val uiState: StateFlow<BasketUiState> = _uiState.asStateFlow()

    private var observeJob: Job? = null

    init {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
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

        refreshBasket(date)
    }

    fun refreshBasket(date: LocalDate) {
        viewModelScope.launch {
            _uiState.value = BasketUiState.Loading
            try {
                refreshBasketUseCase(date)
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