package com.ab.domain.usecases

import com.ab.core.utils.base.Resource
import com.ab.core.utils.error.ExceptionHandler
import com.ab.core.utils.error.Failure
import com.ab.core.utils.resource.ResourceProviderImpl
import com.ab.domain.mapper.toCityWeatherForecast
import com.ab.domain.model.model.CityWeatherForecast
import com.ab.domain.repository.WeatherForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeekCityWeatherForecastByIdUseCase @Inject constructor(
    private val repository: WeatherForecastRepository,
    private val exceptionHandler: ExceptionHandler,
    private val resourceProviderImpl: ResourceProviderImpl
) {
    operator fun invoke(cityId: Long): Flow<Resource<CityWeatherForecast>> = flow {
        emit(Resource.Loading)
        val cityWeatherForecastEntity = repository.getCityWeatherForecastById(cityId)
        if (cityWeatherForecastEntity == null) {
            val failure = Failure.NotFound(
                message = resourceProviderImpl.getString(com.ab.core.R.string.no_city_found_with_this_name),
                cause = Throwable()
            )
            emit(Resource.Fail(failure))
            return@flow
        }

        val cityWeatherForecast = cityWeatherForecastEntity.toCityWeatherForecast()
        emit(Resource.Success(cityWeatherForecast))
    }.catch { throwable ->
        val failure = exceptionHandler.handleAsFailure(throwable)
        emit(Resource.Fail(failure))
    }
}