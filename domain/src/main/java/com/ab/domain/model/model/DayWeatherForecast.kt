package com.ab.domain.model.model

data class DayWeatherForecast(
    val id: Long,
    val name: String,
    val country: String,
    val population: Long,
    val longitude: Double,
    val latitude: Double,
    val weatherForecast: WeatherForecast,
)
