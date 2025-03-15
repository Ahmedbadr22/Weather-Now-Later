package com.ab.domain.usecases

import com.ab.core.R
import com.ab.core.utils.base.Resource
import com.ab.core.utils.error.ExceptionHandler
import com.ab.core.utils.error.Failure
import com.ab.core.utils.isToday
import com.ab.core.utils.resource.ResourceProvider
import com.ab.domain.mapper.toModel
import com.ab.domain.model.model.TodayWeatherForecast
import com.ab.domain.repository.WeatherForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetTodayWeatherForecastByCityUseCase(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val resourceProvider: ResourceProvider,
    private val exceptionHandler: ExceptionHandler
) {
    operator fun invoke(cityName: String): Flow<Resource<TodayWeatherForecast>> = flow {
        emit(Resource.Loading)
        val cityWeatherForecast = weatherForecastRepository.getCityWeatherForecastByName(cityName)

        if (cityWeatherForecast == null) {
            val failure = Failure.NotFound(
                message = resourceProvider.getString(R.string.no_city_found_with_this_name),
                cause = Throwable()
            )
            emit(Resource.Fail(failure))
            return@flow
        }

        val currentWeatherForecast = cityWeatherForecast.weatherForecastDetails.find { weatherForecastEntity ->
            isToday(weatherForecastEntity.weatherForecast.dateTimestamp)
        }

        if (currentWeatherForecast == null) {
            val failure = Failure.NotFound(
                message = resourceProvider.getString(R.string.no_weather_forecast_today),
                cause = Throwable()
            )
            emit(Resource.Fail(failure))
            return@flow
        }

        val todayWeatherForecast = TodayWeatherForecast(
            id = cityWeatherForecast.city.id,
            name = cityWeatherForecast.city.name,
            country = cityWeatherForecast.city.country,
            population = cityWeatherForecast.city.population,
            longitude = cityWeatherForecast.city.longitude,
            latitude = cityWeatherForecast.city.latitude,
            weatherForecast = currentWeatherForecast.toModel()
        )

        emit(Resource.Success(todayWeatherForecast))
    }.catch { throwable ->
        val failure = exceptionHandler.handleAsFailure(throwable)
        emit(Resource.Fail(failure))
    }
}