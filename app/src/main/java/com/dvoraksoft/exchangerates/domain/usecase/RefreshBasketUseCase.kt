package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.repository.BasketRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class RefreshBasketUseCase @Inject constructor(
    private val repository: BasketRepository
) {

    suspend operator fun invoke(date: LocalDate) = repository.refreshBasket(date)
}