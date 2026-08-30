package com.dvoraksoft.exchangerates.data.remote

import com.dvoraksoft.exchangerates.data.remote.dto.DynamicDto
import com.dvoraksoft.exchangerates.data.remote.dto.RateDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("exrates/rates")
    suspend fun getRates(
        @Query("periodicity") periodicity: Int = 0,
        @Query("ondate") onDate: String? = null
    ): List<RateDto>

    @GET("exrates/rates/dynamics/{curId}")
    suspend fun getDynamics(
        @Path("curId") curId: Int,
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): List<DynamicDto>
}