package com.dvoraksoft.exchangerates.data.mapper

import com.dvoraksoft.exchangerates.data.remote.DynamicDto
import com.dvoraksoft.exchangerates.domain.entity.Dynamic
import kotlinx.datetime.LocalDate

fun DynamicDto.toDomain(): Dynamic {
    val date = this.date.take(10).let { LocalDate.parse(it) }
    return Dynamic(
        date = date,
        rate = this.curOfficialRate
    )
}