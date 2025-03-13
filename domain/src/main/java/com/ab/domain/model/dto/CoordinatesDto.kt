package com.ab.domain.model.dto

import com.google.gson.annotations.SerializedName

data class CoordinatesDto(
    @SerializedName("lon")
    val longitude: Double,
    @SerializedName("lat")
    val latitude: Double
)