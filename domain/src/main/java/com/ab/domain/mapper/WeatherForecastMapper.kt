package com.ab.domain.mapper

import com.ab.domain.model.dto.WeatherForecastDto
import com.ab.domain.model.dto.WeatherForecastResponseDto
import com.ab.domain.model.entity.WeatherForecastEntity
import com.ab.domain.model.entity.relations.WeatherForecastDetailsEntityRel
import com.ab.domain.model.model.DayWeatherForecast
import com.ab.domain.model.model.Temperature
import com.ab.domain.model.model.WeatherCondition
import com.ab.domain.model.model.WeatherForecast

fun WeatherForecastDto.toEntity(relatedCityId: Long) = WeatherForecastEntity(
    cityId = relatedCityId,
    dateTimestamp = dateTimestamp,
    sunrise = sunrise,
    sunset = sunset,
    pressure = pressure,
    humidity = humidity,
    windSpeed = windSpeed,
    windDirectionInDegree = windDirectionInDegree
)

fun WeatherForecastDetailsEntityRel.toModel() = WeatherForecast(
    id = weatherForecast.id,
    dateTimestamp = weatherForecast.dateTimestamp,
    sunrise = weatherForecast.sunrise,
    sunset = weatherForecast.sunset,
    pressure = weatherForecast.pressure,
    humidity = weatherForecast.humidity,
    windSpeed = weatherForecast.windSpeed,
    windDirectionInDegree = weatherForecast.windDirectionInDegree,
    temperature = temperature.toModel(),
    weatherCondition = weatherCondition.toModel()
)

fun WeatherForecastResponseDto.toDayWeatherForecast(weatherForecast: WeatherForecastDto): DayWeatherForecast {
    return DayWeatherForecast(
        id = city.id,
        name = city.name,
        country = city.country,
        population = city.population,
        longitude = city.coordinates.longitude,
        latitude = city.coordinates.latitude,
        weatherForecast = WeatherForecast(
            dateTimestamp = weatherForecast.dateTimestamp,
            sunrise = weatherForecast.sunrise,
            sunset = weatherForecast.sunset,
            pressure = weatherForecast.pressure,
            humidity = weatherForecast.humidity,
            windSpeed = weatherForecast.windSpeed,
            windDirectionInDegree = weatherForecast.windDirectionInDegree,
            temperature = Temperature(
                dayTemp = weatherForecast.temperature.day,
                minTemp = weatherForecast.temperature.min,
                maxTemp = weatherForecast.temperature.max,
                nightTemp = weatherForecast.temperature.night,
                eveningTemp = weatherForecast.temperature.evening
            ),
            weatherCondition = WeatherCondition(
                weatherId = weatherForecast.weather.first().id,
                main = weatherForecast.weather.first().main,
                description = weatherForecast.weather.first().description,
                icon = weatherForecast.weather.first().icon
            )
        )
    )
}