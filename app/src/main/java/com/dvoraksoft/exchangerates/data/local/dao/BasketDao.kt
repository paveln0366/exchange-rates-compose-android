package com.dvoraksoft.exchangerates.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvoraksoft.exchangerates.data.local.dbModel.BasketDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketDao {

    @Query("SELECT * FROM basket WHERE date = :date")
    fun getBasketByDateFlow(date: String): Flow<BasketDbModel?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBasket(basket: BasketDbModel)
}