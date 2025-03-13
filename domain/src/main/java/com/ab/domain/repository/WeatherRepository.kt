package com.ab.domain.repository

import com.ab.domain.model.dto.WeatherForecastResponseDto

interface WeatherRepository {
    suspend fun fetchWeatherForecastByCityFromRemote(cityName: String): WeatherForecastResponseDto

}