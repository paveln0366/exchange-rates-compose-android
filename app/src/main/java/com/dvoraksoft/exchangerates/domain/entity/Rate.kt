package com.dvoraksoft.exchangerates.domain.entity

data class Rate(
    val id: Int,
    val abbreviation: String,
    val name: String,
    val scale: Int,
    val rate: Double,
    val delta: Double,
    val flagUrl: String
)