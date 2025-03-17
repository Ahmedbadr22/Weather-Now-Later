package com.ab.domain.model.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TemperatureDto(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    @SerializedName("eve")
    val evening: Double,
    @SerializedName("morn")
    val morning: Double
)
