package com.ab.domain.model.model

data class CityWeatherForecast(
    val id: Long,
    val name: String,
    val country: String,
    val population: Long,
    val weatherForecasts: Map<Long, WeatherForecast>
)
