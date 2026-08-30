package com.dvoraksoft.exchangerates.domain.entity

import kotlinx.datetime.LocalDate

data class Dynamic(
    val date: LocalDate,
    val rate: Double
)

enum class PeriodType {
    WEEK,
    MONTH,
    QUARTER
}