package com.ab.domain.mapper

import com.ab.domain.model.dto.TemperatureDto
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.model.Temperature


fun TemperatureDto.toEntity(relatedWeatherForecastId: Long) = TemperatureEntity(
    forecastId = relatedWeatherForecastId,
    dayTemp = day,
    minTemp = min,
    maxTemp = max,
    nightTemp = night,
    eveningTemp = evening,
    morningTemp = morning
)

fun TemperatureEntity.toModel() = Temperature(
    dayTemp = dayTemp,
    minTemp = minTemp,
    maxTemp = maxTemp,
    nightTemp = nightTemp,
    eveningTemp = eveningTemp,
)