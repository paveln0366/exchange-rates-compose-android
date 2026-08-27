package com.dvoraksoft.exchangerates.data.mapper

import com.dvoraksoft.exchangerates.data.remote.RateDto
import com.dvoraksoft.exchangerates.domain.entity.Rate
import java.util.Locale

fun RateDto.toDomain(yesterdayOfficialRate: Double): Rate {

    val delta = this.curOfficialRate - yesterdayOfficialRate

    return Rate(
        id = this.curId,
        abbreviation = this.curAbbreviation,
        name = this.curName,
        scale = this.curScale,
        rate = this.curOfficialRate,
        delta = delta,
        flagUrl = getFlagUrl(this.curAbbreviation)
    )
}

private fun getFlagUrl(abbreviation: String): String {
    val countryCode = abbreviation.take(2).lowercase(Locale.ROOT)
    return "https://flagcdn.com/w80/$countryCode.png"
}