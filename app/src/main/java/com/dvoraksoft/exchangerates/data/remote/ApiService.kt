package com.dvoraksoft.exchangerates.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("exrates/rates")
    suspend fun getRates(
        @Query("periodicity") periodicity: Int,
        @Query("ondate") onDate: String
    ): List<RateDto>
}