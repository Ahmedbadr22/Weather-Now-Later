package com.ab.domain.model.model

data class WeatherCondition(
    val weatherId: Int,
    val main: String,
    val description: String,
    val icon: String
)
