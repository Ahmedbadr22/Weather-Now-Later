package com.ab.domain.model.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.WeatherForecastEntity

data class CityWithWeatherForecastDetailsEntityRel(
    @Embedded val city: CityEntity,
    @Relation(
        entity = WeatherForecastEntity::class,
        parentColumn = "id",
        entityColumn = "cityId"
    )
    val weatherForecastDetails: List<WeatherForecastDetailsEntityRel>
)
