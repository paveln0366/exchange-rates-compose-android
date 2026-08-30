package com.dvoraksoft.exchangerates.domain.repository

import com.dvoraksoft.exchangerates.domain.model.Basket
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface BasketRepository {

    fun getBasketFlow(date: LocalDate): Flow<Basket>

    suspend fun updateBasket(date: LocalDate)
}