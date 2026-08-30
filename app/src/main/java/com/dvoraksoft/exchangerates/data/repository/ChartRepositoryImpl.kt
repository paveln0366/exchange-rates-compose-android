package com.dvoraksoft.exchangerates.data.repository

import com.dvoraksoft.exchangerates.data.mapper.toDomain
import com.dvoraksoft.exchangerates.data.remote.ApiService
import com.dvoraksoft.exchangerates.domain.entity.Dynamic
import com.dvoraksoft.exchangerates.domain.repository.ChartRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class ChartRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ChartRepository {

    override suspend fun getDynamics(startDate: LocalDate, endDate: LocalDate): List<Dynamic> {
        val dynamics = apiService.getDynamics(
            curId = USD_ID,
            startDate = startDate.toString(),
            endDate = endDate.toString()
        )
        return dynamics.map { dynamic -> dynamic.toDomain() }.sortedBy { it.date }
    }

    companion object {

        private const val USD_ID = 431
    }
}