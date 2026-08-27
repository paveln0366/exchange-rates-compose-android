package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.repository.ReteRepository
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(
    private val repository: ReteRepository
) {

    suspend operator fun invoke(): List<Rate> {
        return repository.getRates()
    }
}