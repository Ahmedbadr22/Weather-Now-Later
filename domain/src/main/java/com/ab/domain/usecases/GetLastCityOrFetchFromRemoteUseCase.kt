package com.ab.domain.usecases

import com.ab.core.utils.base.Resource
import com.ab.core.utils.error.ExceptionHandler
import com.ab.core.utils.error.Failure
import com.ab.core.utils.isToday
import com.ab.core.utils.resource.ResourceProvider
import com.ab.domain.mapper.toDayWeatherForecast
import com.ab.domain.model.model.DayWeatherForecast
import com.ab.domain.repository.WeatherForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLastCityOrFetchFromRemoteUseCase @Inject constructor(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
    private val insertWeatherForecastResponseToLocalUseCase: InsertWeatherForecastResponseToLocalUseCase,
    private val exceptionHandler: ExceptionHandler,
    private val resourceProvider: ResourceProvider
) {
    operator fun invoke(cityName: String? = null): Flow<Resource<DayWeatherForecast>> = flow {
        emit(Resource.Loading)
        if (cityName == null) {
            val resource = getLastSearchedCityUseCase()
            emit(resource)
            return@flow
        }

        val weatherForecastDto = weatherForecastRepository.fetchWeatherForecastByCityFromRemote(cityName, 7)
        insertWeatherForecastResponseToLocalUseCase(weatherForecastDto)

        val currentWeatherForecast = weatherForecastDto.forecasts.find { forecastDto ->
            isToday(forecastDto.dateTimestamp)
        }

        if (currentWeatherForecast == null) {
            val failure = Failure.NotFound(
                message = resourceProvider.getString(com.ab.core.R.string.no_weather_forecast_today),
                cause = Throwable()
            )
            emit(Resource.Fail(failure))
            return@flow
        }

        val dayWeatherForecast = weatherForecastDto.toDayWeatherForecast(currentWeatherForecast)
        emit(Resource.Success(dayWeatherForecast))

    }.catch { throwable ->
        val failure = exceptionHandler.handleAsFailure(throwable)
        emit(Resource.Fail(failure))
    }
}