package com.dvoraksoft.exchangerates.presentation.screen.rate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.usecase.GetRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

@HiltViewModel
class RateViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RatesUiState>(RatesUiState.Loading)
    val uiState: StateFlow<RatesUiState> = _uiState.asStateFlow()

    init {
        loadRates()
    }

    fun loadRates() {
        viewModelScope.launch {
            _uiState.value = RatesUiState.Loading
            try {
                val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
                val day = today.day.toString().padStart(2, '0')
                val month = today.month.number.toString().padStart(2, '0')
                val year = today.year

                val rates = getRatesUseCase()

                _uiState.value = RatesUiState.Success(
                    date = "$day.$month.$year",
                    rates = rates
                )
            } catch (e: Exception) {
                _uiState.value = RatesUiState.Error(e.localizedMessage ?: "Loading error")
            }
        }
    }
}

sealed interface RatesUiState {

    data object Loading : RatesUiState

    data class Success(
        val date: String,
        val rates: List<Rate>
    ) : RatesUiState

    data class Error(val message: String) : RatesUiState
}