package com.dvoraksoft.exchangerates.domain.repository

import com.dvoraksoft.exchangerates.domain.entity.Basket
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface BasketRepository {

    fun getBasketFlow(date: LocalDate): Flow<Basket>

    suspend fun refreshBasket(date: LocalDate)
}