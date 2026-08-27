package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(
    private val rateRepository: RateRepository
) {

    suspend operator fun invoke(): List<Rate> {
        return rateRepository.getRates()
    }
}