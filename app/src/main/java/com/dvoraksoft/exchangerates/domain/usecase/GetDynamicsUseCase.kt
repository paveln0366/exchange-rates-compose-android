package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.model.Dynamic
import com.dvoraksoft.exchangerates.domain.model.PeriodType
import com.dvoraksoft.exchangerates.domain.repository.ChartRepository
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import javax.inject.Inject
import kotlin.time.Clock

class GetDynamicsUseCase @Inject constructor(
    private val repository: ChartRepository
) {

    suspend operator fun invoke(period: PeriodType): List<Dynamic> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val startDate = when (period) {
            PeriodType.WEEK -> today.minus(DatePeriod(days = 7))
            PeriodType.MONTH -> today.minus(DatePeriod(months = 1))
            PeriodType.QUARTER -> today.minus(DatePeriod(months = 3))
        }
        return repository.getDynamics(startDate, today)
    }
}