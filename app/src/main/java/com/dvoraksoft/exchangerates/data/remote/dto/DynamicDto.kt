package com.dvoraksoft.exchangerates.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DynamicDto(
    @SerialName("Cur_ID") val curId: Int,
    @SerialName("Date") val date: String,
    @SerialName("Cur_OfficialRate") val curOfficialRate: Double
)