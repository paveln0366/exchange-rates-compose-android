package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.entity.Basket
import com.dvoraksoft.exchangerates.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetBasketFlowUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    operator fun invoke(date: LocalDate): Flow<Basket> = repository.getBasketFlow(date)
}