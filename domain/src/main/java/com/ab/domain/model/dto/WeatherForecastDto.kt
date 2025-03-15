package com.ab.domain.model.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherForecastDto(
    @SerializedName("dt")
    val dateTimestamp: Long,
    val sunrise: Long,
    val sunset: Long,
    @SerializedName("temp")
    val temperature: TemperatureDto,
    val pressure: Int,
    val humidity: Int,
    val weather: List<WeatherConditionDto>,
    @SerializedName("speed")
    val windSpeed: Double,
    @SerializedName("deg")
    val windDirectionInDegree: Int,
)