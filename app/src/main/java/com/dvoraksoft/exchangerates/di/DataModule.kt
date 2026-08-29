package com.dvoraksoft.exchangerates.di

import android.content.Context
import androidx.room.Room
import com.dvoraksoft.exchangerates.data.local.AppDatabase
import com.dvoraksoft.exchangerates.data.local.BasketDao
import com.dvoraksoft.exchangerates.data.local.RateDao
import com.dvoraksoft.exchangerates.data.remote.ApiService
import com.dvoraksoft.exchangerates.data.repository.BasketRepositoryImpl
import com.dvoraksoft.exchangerates.data.repository.RateRepositoryImpl
import com.dvoraksoft.exchangerates.domain.repository.BasketRepository
import com.dvoraksoft.exchangerates.domain.repository.RateRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindRateRepository(
        rateRepositoryImpl: RateRepositoryImpl
    ): RateRepository

    @Binds
    @Singleton
    fun bindBasketRepository(
        basketRepositoryImpl: BasketRepositoryImpl
    ): BasketRepository

    companion object {

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }

        @Provides
        @Singleton
        fun provideConverterFactory(
            json: Json
        ): Converter.Factory {
            val contentType = "application/json".toMediaType()
            return json.asConverterFactory(contentType)
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            converterFactory: Converter.Factory
        ): Retrofit {
            val baseUrl = "https://api.nbrb.by/"
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(
            retrofit: Retrofit
        ): ApiService {
            return retrofit.create()
        }

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "database.db"
            ).fallbackToDestructiveMigration(true).build()
        }

        @Provides
        @Singleton
        fun provideRateDao(
            database: AppDatabase
        ): RateDao {
            return database.rateDao()
        }

        @Provides
        @Singleton
        fun provideBasketDao(
            database: AppDatabase
        ): BasketDao {
            return database.basketDao()
        }
    }
}