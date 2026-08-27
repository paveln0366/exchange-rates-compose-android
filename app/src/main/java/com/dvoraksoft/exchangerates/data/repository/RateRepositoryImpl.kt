package com.dvoraksoft.exchangerates.data.repository

import com.dvoraksoft.exchangerates.data.mapper.toDomain
import com.dvoraksoft.exchangerates.data.remote.ApiService
import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RateRepository {

    override suspend fun getRates(): List<Rate> {

        val todayRates = apiService.getRates()

        return todayRates.map { todayRate ->
            todayRate.toDomain(yesterdayOfficialRate = todayRate.curOfficialRate)
        }
    }
}