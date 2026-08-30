package com.dvoraksoft.exchangerates.data.repository

import com.dvoraksoft.exchangerates.data.local.dao.BasketDao
import com.dvoraksoft.exchangerates.data.local.dbModel.BasketDbModel
import com.dvoraksoft.exchangerates.data.mapper.toDomain
import com.dvoraksoft.exchangerates.data.remote.ApiService
import com.dvoraksoft.exchangerates.domain.model.Basket
import com.dvoraksoft.exchangerates.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.round

class BasketRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val basketDao: BasketDao
) : BasketRepository {

    override fun getBasketFlow(date: LocalDate): Flow<Basket> {
        return basketDao.getBasketByDateFlow(date.toString()).filterNotNull().map { it.toDomain() }
    }

    override suspend fun refreshBasket(date: LocalDate) {
        val dateString = date.toString()
        val prevYearString = LocalDate(date.year - 1, 12, 31).toString()
        val prevDayString = date.minus(DatePeriod(days = 1)).toString()

        val todayRates = apiService.getRates(onDate = dateString)
        val prevYearRates = apiService.getRates(onDate = prevYearString)
        val prevDayRates = apiService.getRates(onDate = prevDayString)

        val rubRate = todayRates.find { it.curAbbreviation == RUB }?.curOfficialRate ?: 0.0
        val usdRate = todayRates.find { it.curAbbreviation == USD }?.curOfficialRate ?: 0.0
        val cnyRate = todayRates.find { it.curAbbreviation == CNY }?.curOfficialRate ?: 0.0
        val basketValue = calculateBasketValue(rubRate, usdRate, cnyRate)

        val rubPrevYear = prevYearRates.find { it.curAbbreviation == RUB }?.curOfficialRate ?: 0.0
        val usdPrevYear = prevYearRates.find { it.curAbbreviation == USD }?.curOfficialRate ?: 0.0
        val cnyPrevYear = prevYearRates.find { it.curAbbreviation == CNY }?.curOfficialRate ?: 0.0
        val basketPrevYear = calculateBasketValue(rubPrevYear, usdPrevYear, cnyPrevYear)

        val rubPrevDay = prevDayRates.find { it.curAbbreviation == RUB }?.curOfficialRate ?: 0.0
        val usdPrevDay = prevDayRates.find { it.curAbbreviation == USD }?.curOfficialRate ?: 0.0
        val cnyPrevDay = prevDayRates.find { it.curAbbreviation == CNY }?.curOfficialRate ?: 0.0
        val basketPrevDay = calculateBasketValue(rubPrevDay, usdPrevDay, cnyPrevDay)

        val rubChangePrevYear = calculatePercent(rubRate, rubPrevYear)
        val rubChangePrevDay = calculatePercent(rubRate, rubPrevDay)

        val usdChangePrevYear = calculatePercent(usdRate, usdPrevYear)
        val usdChangePrevDay = calculatePercent(usdRate, usdPrevDay)

        val cnyChangePrevYear = calculatePercent(cnyRate, cnyPrevYear)
        val cnyChangePrevDay = calculatePercent(cnyRate, cnyPrevDay)

        val basketChangePrevYear = calculatePercent(basketValue, basketPrevYear)
        val basketChangePrevDay = calculatePercent(basketValue, basketPrevDay)

        val basket = BasketDbModel(
            date = dateString,
            basketValue = basketValue,
            basketChangePrevYear = basketChangePrevYear,
            basketChangePrevDay = basketChangePrevDay,
            rubRate = rubRate,
            rubChangePrevYear = rubChangePrevYear,
            rubChangePrevDay = rubChangePrevDay,
            usdRate = usdRate,
            usdChangePrevYear = usdChangePrevYear,
            usdChangePrevDay = usdChangePrevDay,
            cnyRate = cnyRate,
            cnyChangePrevYear = cnyChangePrevYear,
            cnyChangePrevDay = cnyChangePrevDay
        )

        basketDao.insertBasket(basket)
    }

    private fun calculateBasketValue(rub: Double, usd: Double, cny: Double): Double {
        val rubValue = rub / 100.0
        val usdValue = usd / 1.0
        val cnyValue = cny / 10.0
        val result = (rubValue.pow(0.6)) * (usdValue.pow(0.3)) * (cnyValue.pow(0.1))
        return round(result * 10000.0) / 10000.0
    }

    private fun calculatePercent(today: Double, prev: Double): Double {
        if (prev == 0.0 || today == 0.0) return 0.0
        return ((prev - today) / prev) * 100.0
    }

    companion object {

        private const val RUB = "RUB"
        private const val USD = "USD"
        private const val CNY = "CNY"
    }
}