package com.dvoraksoft.exchangerates.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket")
data class BasketEntity(
    @PrimaryKey
    val date: String,

    val basketValue: Double,
    val basketChangePrevYear: Double,
    val basketChangePrevDay: Double,

    val rubRate: Double,
    val rubChangePrevYear: Double,
    val rubChangePrevDay: Double,

    val usdRate: Double,
    val usdChangePrevYear: Double,
    val usdChangePrevDay: Double,

    val cnyRate: Double,
    val cnyChangePrevYear: Double,
    val cnyChangePrevDay: Double
)