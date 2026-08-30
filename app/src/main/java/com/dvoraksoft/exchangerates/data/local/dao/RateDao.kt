package com.dvoraksoft.exchangerates.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dvoraksoft.exchangerates.data.local.dbModel.RateDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Query("SELECT * FROM rates")
    fun getRatesFlow(): Flow<List<RateDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(rates: List<RateDbModel>)
}