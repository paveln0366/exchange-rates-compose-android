package com.dvoraksoft.exchangerates.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dvoraksoft.exchangerates.data.local.dao.BasketDao
import com.dvoraksoft.exchangerates.data.local.dao.RateDao
import com.dvoraksoft.exchangerates.data.local.dbModel.BasketDbModel
import com.dvoraksoft.exchangerates.data.local.dbModel.RateDbModel

@Database(
    entities = [RateDbModel::class, BasketDbModel::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao

    abstract fun basketDao(): BasketDao
}