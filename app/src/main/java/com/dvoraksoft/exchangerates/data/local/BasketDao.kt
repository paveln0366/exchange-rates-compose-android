package com.dvoraksoft.exchangerates.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {

    @Query("SELECT * FROM basket WHERE date = :date")
    fun getBasketByDateFlow(date: String): Flow<BasketEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasket(basket: BasketEntity)
}