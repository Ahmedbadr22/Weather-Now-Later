package com.ab.domain.model.model

data class CityWeatherForecast(
    val id: Long,
    val name: String,
    val country: String,
    val weatherForecasts: List<WeatherForecast>
)
