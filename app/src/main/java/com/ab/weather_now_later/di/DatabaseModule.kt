package com.ab.weather_now_later.di

import android.app.Application
import android.content.Context
import com.ab.data.source.local.db.WeatherNowLaterDatabase
import com.ab.data.source.local.db.dao.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeatherNowLaterDatabase(@ApplicationContext context: Context): WeatherNowLaterDatabase {
        return WeatherNowLaterDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCityDao(db: WeatherNowLaterDatabase): CityDao = db.getCityDao()

    @Provides
    @Singleton
    fun provideWeatherForecastDao(db: WeatherNowLaterDatabase) = db.getWeatherForecastDao()

    @Provides
    @Singleton
    fun provideTemperatureDao(db: WeatherNowLaterDatabase) = db.getTemperatureDao()

    @Provides
    @Singleton
    fun provideWeatherConditionDao(db: WeatherNowLaterDatabase) = db.getWeatherConditionDao()
}