package com.dvoraksoft.exchangerates.domain.repository

import com.dvoraksoft.exchangerates.domain.entity.Rate
import kotlinx.coroutines.flow.Flow

interface RateRepository {

    fun getRatesFlow(): Flow<List<Rate>>

    suspend fun refreshRates()

    // TODO: Not used
    suspend fun getRates(): List<Rate>
}