package com.dvoraksoft.exchangerates.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates")
data class RateEntity(
    @PrimaryKey
    val abbreviation: String,
    val curId: Int,
    val name: String,
    val scale: Int,
    val rate: Double,
    val delta: Double,
    val flagUrl: String,
    val lastUpdated: Long = System.currentTimeMillis()
)