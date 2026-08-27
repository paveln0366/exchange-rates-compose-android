package com.dvoraksoft.exchangerates.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun rateDao(): RateDao
}