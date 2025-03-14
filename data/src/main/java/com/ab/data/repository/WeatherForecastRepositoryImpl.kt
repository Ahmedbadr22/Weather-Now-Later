package com.ab.data.repository

import com.ab.data.source.remote.datasource.WeatherForecastRemoteDataSource
import com.ab.domain.model.dto.WeatherForecastResponseDto
import com.ab.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource
) : WeatherForecastRepository {

    override suspend fun fetchWeatherForecastByCityFromRemote(
        cityName: String,
        dayCount: Int
    ): WeatherForecastResponseDto {
        return weatherForecastRemoteDataSource.fetchWeatherNDaysForecastByCity(cityName, dayCount)
    }
}