package com.dvoraksoft.exchangerates.domain.usecase

import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRatesFlowUseCase @Inject constructor(
    private val repository: RateRepository
) {

    operator fun invoke(): Flow<List<Rate>> = repository.getRatesFlow()
}