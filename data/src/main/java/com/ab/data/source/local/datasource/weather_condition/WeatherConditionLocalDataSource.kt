package com.ab.data.source.local.datasource.weather_condition

import com.ab.domain.model.entity.WeatherConditionEntity

interface WeatherConditionLocalDataSource {
    suspend fun insert(entity: WeatherConditionEntity): Long
}