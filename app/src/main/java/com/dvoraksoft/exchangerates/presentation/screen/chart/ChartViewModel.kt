package com.dvoraksoft.exchangerates.presentation.screen.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvoraksoft.exchangerates.domain.entity.Dynamic
import com.dvoraksoft.exchangerates.domain.entity.PeriodType
import com.dvoraksoft.exchangerates.domain.usecase.GetDynamicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.number
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getDynamicsUseCase: GetDynamicsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChartUiState>(ChartUiState.Loading)
    val uiState: StateFlow<ChartUiState> = _uiState.asStateFlow()

    init {
        loadData(PeriodType.WEEK)
    }

    fun onPeriodSelected(period: PeriodType) {
        loadData(period)
    }

    private fun loadData(period: PeriodType) {
        viewModelScope.launch {
            _uiState.value = ChartUiState.Loading
            try {
                val dynamics = getDynamicsUseCase(period)

                val currentDynamic = dynamics.lastOrNull()
                val previousDynamic = dynamics.getOrNull(dynamics.size - 2)

                val rate = currentDynamic?.rate ?: 0.0

                val change = if (currentDynamic != null && previousDynamic != null)
                    currentDynamic.rate - previousDynamic.rate else 0.0

                val updated = currentDynamic?.date?.let { date ->
                    val day = date.day.toString().padStart(2, '0')
                    val month = date.month.number.toString().padStart(2, '0')
                    "$day.$month"
                }

                _uiState.value = ChartUiState.Success(
                    dynamics = dynamics,
                    rate = rate,
                    selectedPeriod = period,
                    change = change,
                    updated = updated
                )
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error(e.localizedMessage ?: "Loading error")
            }
        }
    }
}

sealed interface ChartUiState {

    data object Loading : ChartUiState

    data class Success(
        val dynamics: List<Dynamic>,
        val rate: Double,
        val selectedPeriod: PeriodType,
        val change: Double,
        val updated: String?
    ) : ChartUiState

    data class Error(val message: String) : ChartUiState
}