package com.ab.weather_now_later.di

import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.city.CityLocalDataSourceImpl
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSource
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSource
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSource
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {

    @Binds
    @Singleton
    fun bindWeatherForecastLocalDataSource(localDataSource: WeatherForecastLocalDataSourceImpl): WeatherForecastLocalDataSource

    @Binds
    @Singleton
    fun bindCityLocalDataSource(localDataSource: CityLocalDataSourceImpl): CityLocalDataSource

    @Binds
    @Singleton
    fun bindWeatherConditionLocalDataSource(localDataSource: WeatherConditionLocalDataSourceImpl): WeatherConditionLocalDataSource

    @Binds
    @Singleton
    fun bindTemperatureLocalDataSource(localDataSource: TemperatureLocalDataSourceImpl): TemperatureLocalDataSource
}