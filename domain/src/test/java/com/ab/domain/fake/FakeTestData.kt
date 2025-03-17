package com.ab.domain.fake

import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherConditionEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.random.Random

object FakeTestData {
    fun buildCityEntity(id: Long, name: String) = CityEntity(
        id = id,
        name = name,
        country = "EG",
        longitude = 31.0,
        latitude = 30.0,
        population = 9000000,
        lastSearchDate = LocalDateTime.now(),
        timezone = 0
    )

    fun buildForecastEntity(cityId: Long, today: Boolean) = WeatherForecastEntity(
        id = 1L,
        cityId = cityId,
        dateTimestamp = if (today)
            LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
        else
            LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC).toEpochMilli(),
        sunrise = 0L,
        sunset = 0L,
        pressure = 0,
        humidity = 0,
        windSpeed = 0.0,
        windDirectionInDegree = 0
    )

    fun buildTemperatureEntity(forecastId: Long) = TemperatureEntity(
        id = 1L,
        forecastId = forecastId,
        dayTemp = 25.0,
        minTemp = 18.0,
        maxTemp = 27.0,
        nightTemp = 19.0,
        eveningTemp = 22.0,
        morningTemp = 20.0
    )

    fun buildConditionEntity(forecastId: Long) = WeatherConditionEntity(
        id = 1L,
        forecastId = forecastId,
        main = "Clear",
        description = "Sunny",
        icon = "01d",
        weatherId = Random(3).nextInt()
    )
}
