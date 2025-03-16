package com.ab.data.repository

import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSource
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSource
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSource
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel
import com.ab.data.source.remote.datasource.WeatherForecastRemoteDataSource
import com.ab.domain.model.dto.WeatherForecastResponseDto
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherConditionEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import com.ab.domain.repository.WeatherForecastRepository
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource,
    private val weatherForecastLocalDataSource: WeatherForecastLocalDataSource,
    private val cityLocalDataSource: CityLocalDataSource,
    private val temperatureLocalDataSource: TemperatureLocalDataSource,
    private val weatherConditionLocalDataSource: WeatherConditionLocalDataSource
) : WeatherForecastRepository {

    override suspend fun fetchWeatherForecastByCityFromRemote(
        cityName: String,
        dayCount: Int
    ): WeatherForecastResponseDto {
        return weatherForecastRemoteDataSource.fetchWeatherNDaysForecastByCity(cityName, dayCount)
    }

    override suspend fun insertCityToLocal(entity: CityEntity): Long {
        return cityLocalDataSource.insert(entity)
    }

    override suspend fun insertWeatherForecastToLocal(entity: WeatherForecastEntity): Long {
        return weatherForecastLocalDataSource.insert(entity)
    }

    override suspend fun insertWeatherConditionToLocal(entity: WeatherConditionEntity): Long {
        return weatherConditionLocalDataSource.insert(entity)
    }

    override suspend fun insertTemperatureToLocal(entity: TemperatureEntity): Long {
        return temperatureLocalDataSource.insert(entity)
    }

    override suspend fun getCityWeatherForecastByName(name: String): CityWithWeatherForecastDetailsEntityRel? {
        return cityLocalDataSource.getCityWeatherForecastByName(name)
    }

    override suspend fun getLastSearchedCity(): CityWithWeatherForecastDetailsEntityRel? {
        return cityLocalDataSource.getLastSearchedCity()
    }
}