package com.dvoraksoft.exchangerates.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RateEntity::class, BasketEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao

    abstract fun basketDao(): BasketDao
}