package com.ab.domain.repository

import com.ab.domain.model.dto.WeatherForecastResponseDto
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherConditionEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel

interface WeatherForecastRepository {
    suspend fun fetchWeatherForecastByCityFromRemote(cityName: String, dayCount: Int): WeatherForecastResponseDto

    suspend fun insertCityToLocal(entity: CityEntity): Long
    suspend fun insertWeatherForecastToLocal(entity: WeatherForecastEntity): Long
    suspend fun insertWeatherConditionToLocal(entity: WeatherConditionEntity): Long
    suspend fun insertTemperatureToLocal(entity: TemperatureEntity): Long

    suspend fun getCityWeatherForecastByName(name: String): CityWithWeatherForecastDetailsEntityRel?
    suspend fun getLastSearchedCity(): CityWithWeatherForecastDetailsEntityRel?
}