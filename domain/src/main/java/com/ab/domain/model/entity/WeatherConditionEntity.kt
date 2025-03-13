package com.ab.domain.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.ab.core.utils.constants.DB.WEATHER_CONDITION_TABLE_NAME

@Entity(
    tableName = WEATHER_CONDITION_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = WeatherForecastEntity::class,
            parentColumns = ["id"],
            childColumns = ["forecastId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WeatherConditionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val forecastId: Long,
    val weatherId: Int,
    val main: String,
    val description: String,
    val icon: String
)