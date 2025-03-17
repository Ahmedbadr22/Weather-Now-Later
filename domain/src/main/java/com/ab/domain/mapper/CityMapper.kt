package com.ab.domain.mapper

import com.ab.domain.model.dto.CityDto
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel
import com.ab.domain.model.entity.relations.WeatherForecastDetailsEntityRel
import com.ab.domain.model.model.CityWeatherForecast

fun CityDto.toEntity(): CityEntity = CityEntity(
    id = this.id,
    name = this.name,
    country = this.country,
    longitude = coordinates.longitude,
    latitude = coordinates.latitude,
    population = population,
    timezone = timezone
)

fun CityWithWeatherForecastDetailsEntityRel.toCityWeatherForecast(): CityWeatherForecast {
    return CityWeatherForecast(
        id = city.id,
        name = city.name,
        country = city.country,
        weatherForecasts = weatherForecastDetails.map(WeatherForecastDetailsEntityRel::toModel)
    )
}