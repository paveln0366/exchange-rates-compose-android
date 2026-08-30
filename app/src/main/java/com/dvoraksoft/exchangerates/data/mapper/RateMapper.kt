package com.dvoraksoft.exchangerates.data.mapper

import com.dvoraksoft.exchangerates.data.local.dbModel.RateDbModel
import com.dvoraksoft.exchangerates.data.remote.dto.RateDto
import com.dvoraksoft.exchangerates.domain.model.Rate
import java.util.Locale

fun RateDto.toEntity(yesterdayOfficialRate: Double): RateDbModel {

    val currentRate = this.curOfficialRate
    val delta = currentRate - yesterdayOfficialRate

    return RateDbModel(
        abbreviation = this.curAbbreviation,
        curId = this.curId,
        name = this.curName,
        scale = this.curScale,
        rate = currentRate,
        delta = delta,
        flagUrl = getFlagUrl(this.curAbbreviation)
    )
}

fun RateDbModel.toDomain(): Rate {
    return Rate(
        id = this.curId,
        abbreviation = this.abbreviation,
        name = this.name,
        scale = this.scale,
        rate = this.rate,
        delta = this.delta,
        flagUrl = this.flagUrl
    )
}

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