package com.dvoraksoft.exchangerates.data.repository

import com.dvoraksoft.exchangerates.data.mapper.toDomain
import com.dvoraksoft.exchangerates.data.remote.ApiService
import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class RateRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RateRepository {

    override suspend fun getRates(): List<Rate> {

        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val yesterday = today.minus(1, DateTimeUnit.DAY)

        val todayRates = apiService.getRates()
        val yesterdayRates = apiService.getRates(onDate = yesterday.toString())

        return todayRates.map { todayRate ->
            val yesterdayRate = yesterdayRates.find { yesterdayRate ->
                yesterdayRate.curAbbreviation == todayRate.curAbbreviation
            }
            todayRate.toDomain(yesterdayOfficialRate = yesterdayRate?.curOfficialRate ?: 0.0)
        }
    }
}