package com.ab.domain.repository

import com.ab.domain.model.dto.WeatherForecastResponseDto

interface WeatherForecastRepository {
    suspend fun fetchWeatherForecastByCityFromRemote(cityName: String, dayCount: Int): WeatherForecastResponseDto

}