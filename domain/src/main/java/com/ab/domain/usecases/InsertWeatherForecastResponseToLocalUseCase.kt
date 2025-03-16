package com.ab.domain.usecases

import com.ab.domain.mapper.toEntity
import com.ab.domain.model.dto.WeatherForecastResponseDto
import com.ab.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class InsertWeatherForecastResponseToLocalUseCase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
) {

    suspend operator fun invoke(weatherForecastResponse: WeatherForecastResponseDto) {
        val cityEntity = weatherForecastResponse.city.toEntity()
        val insertedCityId = weatherForecastRepository.insertCityToLocal(cityEntity)

        weatherForecastResponse.forecasts.forEach { weatherForecastDto ->
            val weatherForecastEntity = weatherForecastDto.toEntity(insertedCityId)
            val insertedWeatherForecastId = weatherForecastRepository.insertWeatherForecastToLocal(weatherForecastEntity)

            weatherForecastDto.temperature.toEntity(insertedWeatherForecastId).also { temperatureEntity ->
                weatherForecastRepository.insertTemperatureToLocal(temperatureEntity)
            }

            weatherForecastDto.weather.forEach { weatherConditionDto ->
                weatherConditionDto.toEntity(insertedWeatherForecastId).also { weatherConditionEntity ->
                    weatherForecastRepository.insertWeatherConditionToLocal(weatherConditionEntity)
                }
            }
        }
    }
}