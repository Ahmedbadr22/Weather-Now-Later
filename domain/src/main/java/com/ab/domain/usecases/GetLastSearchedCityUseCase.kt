package com.ab.domain.usecases

import com.ab.core.utils.base.Resource
import com.ab.core.utils.error.Failure
import com.ab.core.utils.isToday
import com.ab.core.utils.resource.ResourceProvider
import com.ab.domain.mapper.toModel
import com.ab.domain.model.model.DayWeatherForecast
import com.ab.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class GetLastSearchedCityUseCase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(): Resource<DayWeatherForecast> {
        val lastSearchedCity = weatherForecastRepository.getLastSearchedCity()

        if (lastSearchedCity == null) {
            val failure = Failure.NotFound(
                message = resourceProvider.getString(com.ab.core.R.string.no_city_found_with_this_name),
                cause = Throwable()
            )
            return Resource.Fail(failure)
        }

        val currentWeatherForecast = lastSearchedCity.weatherForecastDetails.find { weatherForecastEntity ->
            isToday(weatherForecastEntity.weatherForecast.dateTimestamp)
        }

        if (currentWeatherForecast == null) {
            val failure = Failure.NotFound(
                message = resourceProvider.getString(com.ab.core.R.string.no_weather_forecast_today),
                cause = Throwable()
            )
            return Resource.Fail(failure)
        }

        val dayWeatherForecast = DayWeatherForecast(
            id = lastSearchedCity.city.id,
            name = lastSearchedCity.city.name,
            country = lastSearchedCity.city.country,
            population = lastSearchedCity.city.population,
            longitude = lastSearchedCity.city.longitude,
            latitude = lastSearchedCity.city.latitude,
            weatherForecast = currentWeatherForecast.toModel()
        )
        return Resource.Success(dayWeatherForecast)
    }
}