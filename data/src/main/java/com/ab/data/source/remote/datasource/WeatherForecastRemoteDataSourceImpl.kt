package com.ab.data.source.remote.datasource

import com.ab.data.source.remote.service.WeatherForecastClientService
import com.ab.domain.model.dto.WeatherForecastResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherForecastRemoteDataSourceImpl @Inject constructor(
    private val weatherForecastClientService: WeatherForecastClientService
): WeatherForecastRemoteDataSource {

    override suspend fun fetchWeatherNDaysForecastByCity(
        cityName: String,
        dayCount: Int
    ): WeatherForecastResponseDto {
        return withContext(Dispatchers.IO) {
            weatherForecastClientService.fetchWeatherNDaysForecastByCity(
                cityName = cityName,
                dayCount = dayCount
            )
        }
    }
}