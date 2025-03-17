package com.ab.domain.model.dto

import androidx.annotation.Keep

@Keep
data class WeatherConditionDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)