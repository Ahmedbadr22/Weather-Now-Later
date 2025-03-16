package com.ab.domain.model.model

data class WeatherForecast(
    val id: Long = 0,
    val dateTimestamp: Long,
    val sunrise: Long,
    val sunset: Long,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDirectionInDegree: Int,
    val temperature: Temperature,
    val weatherCondition: WeatherCondition
)
