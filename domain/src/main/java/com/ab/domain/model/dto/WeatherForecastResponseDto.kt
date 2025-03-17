package com.ab.domain.model.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponseDto(
    val city: CityDto,
    @SerializedName("cod")
    val statusCode: String,
    val message: Double,
    @SerializedName("cnt")
    val count: Int,
    @SerializedName("list")
    val forecasts: List<WeatherForecastDto>
)