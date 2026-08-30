package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import javax.inject.Inject

class UpdateRatesUseCase @Inject constructor(
    private val rateRepository: RateRepository
) {

    suspend operator fun invoke() = rateRepository.updateRates()
}