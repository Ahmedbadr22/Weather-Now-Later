package com.ab.domain.mapper

import com.ab.domain.model.dto.WeatherForecastDto
import com.ab.domain.model.entity.WeatherForecastEntity
import com.ab.domain.model.entity.relations.WeatherForecastDetailsEntityRel
import com.ab.domain.model.model.WeatherForecast

fun WeatherForecastDto.toEntity(relatedCityId: Long) = WeatherForecastEntity(
    cityId = relatedCityId,
    dateTimestamp = dateTimestamp,
    sunrise = sunrise,
    sunset = sunset,
    pressure = pressure,
    humidity = humidity,
    windSpeed = windSpeed,
    windDirectionInDegree = windDirectionInDegree
)

fun WeatherForecastDetailsEntityRel.toModel() = WeatherForecast(
    id = weatherForecast.id,
    dateTimestamp = weatherForecast.dateTimestamp,
    sunrise = weatherForecast.sunrise,
    sunset = weatherForecast.sunset,
    pressure = weatherForecast.pressure,
    humidity = weatherForecast.humidity,
    windSpeed = weatherForecast.windSpeed,
    windDirectionInDegree = weatherForecast.windDirectionInDegree,
    temperature = temperature.toModel(),
    weatherCondition = weatherCondition.toModel()
)