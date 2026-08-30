package com.dvoraksoft.exchangerates.domain.repository

import com.dvoraksoft.exchangerates.domain.model.Dynamic
import kotlinx.datetime.LocalDate

interface ChartRepository {

    suspend fun getDynamics(startDate: LocalDate, endDate: LocalDate): List<Dynamic>
}