package com.dvoraksoft.exchangerates.data.mapper

import com.dvoraksoft.exchangerates.data.local.BasketEntity
import com.dvoraksoft.exchangerates.domain.entity.Basket
import com.dvoraksoft.exchangerates.domain.entity.BasketItem

fun BasketEntity.toDomain(): Basket {
    return Basket(
        date = this.date,
        basket = BasketItem(
            "Корзина валют",
            this.basketValue,
            this.basketChangePrevYear,
            this.basketChangePrevDay
        ),
        rub = BasketItem(
            "100 Российских рублей",
            this.rubRate,
            this.rubChangePrevYear,
            this.rubChangePrevDay
        ),
        usd = BasketItem(
            "1 Доллар США",
            this.usdRate,
            this.usdChangePrevYear,
            this.usdChangePrevDay
        ),
        cny = BasketItem(
            "10 Китайских юаней",
            this.cnyRate,
            this.cnyChangePrevYear,
            this.cnyChangePrevDay
        )
    )
}