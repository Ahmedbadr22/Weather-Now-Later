package com.ab.domain.mapper

import com.ab.domain.model.dto.WeatherConditionDto
import com.ab.domain.model.entity.WeatherConditionEntity
import com.ab.domain.model.model.WeatherCondition

fun WeatherConditionDto.toEntity(relatedWeatherForecastId: Long) = WeatherConditionEntity(
    forecastId = relatedWeatherForecastId,
    weatherId = id,
    main = main,
    description = description,
    icon = icon
)

fun WeatherConditionEntity.toModel() = WeatherCondition(
    weatherId = weatherId,
    main = main,
    description = description,
    icon = icon
)