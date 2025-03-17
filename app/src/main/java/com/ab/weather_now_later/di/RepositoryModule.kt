package com.ab.weather_now_later.di

import com.ab.data.repository.WeatherForecastRepositoryImpl
import com.ab.domain.repository.WeatherForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindWeatherForecastRepository(repository: WeatherForecastRepositoryImpl): WeatherForecastRepository

}