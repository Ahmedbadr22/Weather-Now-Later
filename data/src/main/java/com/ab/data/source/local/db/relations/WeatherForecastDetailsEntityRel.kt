package com.ab.data.source.local.db.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherConditionEntity
import com.ab.domain.model.entity.WeatherForecastEntity


data class WeatherForecastDetailsEntityRel(
    @Embedded
    val weatherForecast: WeatherForecastEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "forecastId"
    )
    val temperature: TemperatureEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "forecastId"
    )
    val weatherCondition: WeatherConditionEntity
)