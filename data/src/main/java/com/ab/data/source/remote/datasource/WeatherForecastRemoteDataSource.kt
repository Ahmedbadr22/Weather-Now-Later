package com.ab.data.source.remote.datasource

import com.ab.domain.model.dto.WeatherForecastResponseDto

interface WeatherForecastRemoteDataSource {
    suspend fun fetchWeatherNDaysForecastByCity(cityName: String, dayCount: Int): WeatherForecastResponseDto
}