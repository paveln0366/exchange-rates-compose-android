package com.dvoraksoft.exchangerates.data.repository

import com.dvoraksoft.exchangerates.data.local.dao.RateDao
import com.dvoraksoft.exchangerates.data.mapper.toDbModel
import com.dvoraksoft.exchangerates.data.mapper.toDomain
import com.dvoraksoft.exchangerates.data.remote.ApiService
import com.dvoraksoft.exchangerates.domain.model.Rate
import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class RateRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val rateDao: RateDao
) : RateRepository {

    override fun getRatesFlow(): Flow<List<Rate>> {
        return rateDao.getRatesFlow().map { rateDbModels -> rateDbModels.map { it.toDomain() } }
    }

    override suspend fun updateRates() {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val yesterday = today.minus(1, DateTimeUnit.DAY)

        val todayRates = apiService.getRates()
        val yesterdayRates = apiService.getRates(onDate = yesterday.toString())

        val rateDbModels = todayRates.map { todayRate ->
            val yesterdayRate = yesterdayRates.find { yesterdayRate ->
                yesterdayRate.curAbbreviation == todayRate.curAbbreviation
            }
            todayRate.toDbModel(yesterdayRate?.curOfficialRate ?: 0.0)
        }

        rateDao.insertRates(rateDbModels)
    }
}