package com.dvoraksoft.exchangerates.data.repository

import com.dvoraksoft.exchangerates.data.remote.ApiService
import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import javax.inject.Inject

class RateRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RateRepository {

    override suspend fun getRates(): List<Rate> {
        return emptyList()
    }
}