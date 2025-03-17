package com.ab.data.source.local.datasource.weather_forecast

import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherForecastEntity

interface WeatherForecastLocalDataSource {
    suspend fun insert(entity: WeatherForecastEntity): Long
}