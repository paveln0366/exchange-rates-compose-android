package com.dvoraksoft.exchangerates.domain.entity

data class Basket(
    val date: String,
    val basket: BasketItem,
    val rub: BasketItem,
    val usd: BasketItem,
    val cny: BasketItem
)

data class BasketItem(
    val name: String,
    val rate: Double,
    val changePrevYearPercent: Double,
    val changePrevDayPercent: Double
)