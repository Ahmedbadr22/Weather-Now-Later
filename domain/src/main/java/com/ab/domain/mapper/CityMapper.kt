package com.ab.domain.mapper

import com.ab.domain.model.model.CityWeatherForecast
import com.ab.domain.model.dto.CityDto
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel

fun CityDto.toEntity(): CityEntity = CityEntity(
    id = this.id,
    name = this.name,
    country = this.country,
    longitude = coordinates.longitude,
    latitude = coordinates.latitude,
    population = population,
    timezone = timezone
)