package com.ab.weather_now_later.di

import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.city.CityLocalDataSourceImpl
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSource
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSource
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSource
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSourceImpl
import com.ab.data.source.remote.datasource.WeatherForecastRemoteDataSource
import com.ab.data.source.remote.datasource.WeatherForecastRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    @Singleton
    fun bindWeatherForecastRemoteDataSource(localDataSource: WeatherForecastRemoteDataSourceImpl): WeatherForecastRemoteDataSource

}