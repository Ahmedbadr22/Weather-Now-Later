package com.ab.domain.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ab.core.utils.constants.DB.TEMPERATURE_TABLE_NAME

@Entity(
    tableName = TEMPERATURE_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = WeatherForecastEntity::class,
            parentColumns = ["id"],
            childColumns = ["forecastId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TemperatureEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val forecastId: Long,
    val dayTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val nightTemp: Double,
    val eveningTemp: Double,
    val morningTemp: Double,
)