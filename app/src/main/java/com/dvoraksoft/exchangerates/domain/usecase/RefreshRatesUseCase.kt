package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import javax.inject.Inject

class RefreshRatesUseCase @Inject constructor(
    private val rateRepository: RateRepository
) {

    suspend operator fun invoke() = rateRepository.refreshRates()
}