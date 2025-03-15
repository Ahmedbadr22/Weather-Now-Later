package com.ab.domain.usecases

import com.ab.core.utils.base.Resource
import com.ab.core.utils.error.ExceptionHandler
import com.ab.domain.mapper.toEntity
import com.ab.domain.repository.WeatherForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchWeatherForecastByCityNameUseCase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(
        cityName: String,
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading)
        val weatherForecastResponse = weatherForecastRepository
            .fetchWeatherForecastByCityFromRemote(cityName, 7)

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

        emit(Resource.Success(Unit))
    }.catch { throwable ->
        val failure = exceptionHandler.handleAsFailure(throwable)
        emit(Resource.Fail(failure))
    }
}