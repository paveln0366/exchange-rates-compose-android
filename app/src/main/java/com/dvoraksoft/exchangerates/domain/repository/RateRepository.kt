package com.dvoraksoft.exchangerates.domain.repository

import com.dvoraksoft.exchangerates.domain.model.Rate
import kotlinx.coroutines.flow.Flow

interface RateRepository {

    fun getRatesFlow(): Flow<List<Rate>>

    suspend fun updateRates()
}