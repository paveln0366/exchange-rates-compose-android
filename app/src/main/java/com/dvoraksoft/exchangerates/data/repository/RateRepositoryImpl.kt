package com.dvoraksoft.exchangerates.data.repository

import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.domain.repository.ReteRepository

class RateRepositoryImpl : ReteRepository {

    override suspend fun getRates(): List<Rate> {
        return emptyList()
    }
}