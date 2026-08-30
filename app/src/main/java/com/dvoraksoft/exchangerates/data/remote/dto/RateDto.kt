package com.dvoraksoft.exchangerates.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RateDto(
    @SerialName("Cur_ID") val curId: Int,
    @SerialName("Date") val date: String,
    @SerialName("Cur_Abbreviation") val curAbbreviation: String,
    @SerialName("Cur_Scale") val curScale: Int,
    @SerialName("Cur_Name") val curName: String,
    @SerialName("Cur_OfficialRate") val curOfficialRate: Double
)