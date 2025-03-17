package com.ab.domain.model.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    val id: Long,
    val name: String,
    @SerializedName("coord")
    val coordinates: CoordinatesDto,
    val country: String,
    val population: Long,
    val timezone: Int
)