package com.ab.domain.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ab.core.utils.constants.DB.WEATHER_FORECAST_TABLE_NAME

@Entity(
    tableName = WEATHER_FORECAST_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["cityId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WeatherForecastEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cityId: Long,
    val dateTimestamp: Long,
    val sunrise: Long,
    val sunset: Long,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val windDirectionInDegree: Int,
)
