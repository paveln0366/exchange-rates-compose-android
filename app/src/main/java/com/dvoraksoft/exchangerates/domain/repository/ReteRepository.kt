package com.dvoraksoft.exchangerates.domain.repository

import com.dvoraksoft.exchangerates.domain.entity.Rate

interface ReteRepository {

    suspend fun getRates(): List<Rate>
}