package com.ab.domain

import com.ab.domain.model.dto.CityDto
import com.ab.domain.model.dto.CoordinatesDto
import com.ab.domain.model.dto.TemperatureDto
import com.ab.domain.model.dto.WeatherConditionDto
import com.ab.domain.model.dto.WeatherForecastDto
import com.ab.domain.model.dto.WeatherForecastResponseDto
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherConditionEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel
import com.ab.domain.model.entity.relations.WeatherForecastDetailsEntityRel
import com.ab.domain.repository.WeatherForecastRepository
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class FakeWeatherForecastRepository : WeatherForecastRepository {

    val insertedCities = mutableListOf<CityEntity>()
    val insertedForecasts = mutableListOf<WeatherForecastEntity>()
    val insertedTemperatures = mutableListOf<TemperatureEntity>()
    val insertedConditions = mutableListOf<WeatherConditionEntity>()

    override suspend fun insertCityToLocal(entity: CityEntity): Long {
        insertedCities.add(entity)
        return entity.id
    }

    override suspend fun insertWeatherForecastToLocal(entity: WeatherForecastEntity): Long {
        insertedForecasts.add(entity)
        return entity.id
    }

    override suspend fun insertTemperatureToLocal(entity: TemperatureEntity): Long {
        insertedTemperatures.add(entity)
        return entity.id
    }

    override suspend fun insertWeatherConditionToLocal(entity: WeatherConditionEntity): Long {
        insertedConditions.add(entity)
        return entity.id
    }

    override suspend fun fetchWeatherForecastByCityFromRemote(cityName: String, dayCount: Int): WeatherForecastResponseDto {
        return WeatherForecastResponseDto(
            city = CityDto(
                id = 1,
                name = cityName,
                country = "FAKE",
                coordinates = CoordinatesDto(0.0, 0.0),
                population = 0,
                timezone = 0
            ),
            statusCode = "200",
            message = 0.0,
            count = dayCount,
            forecasts = List(dayCount) { index ->
                WeatherForecastDto(
                    dateTimestamp = 1234567890L + index,
                    temperature = TemperatureDto(22.5, 18.0, 25.0, 20.0, 22.0, 19.0),
                    weather = listOf(
                        WeatherConditionDto(
                            id = 1,
                            main = "Clear",
                            description = "Clear sky",
                            icon = "01d"
                        )
                    ),
                    sunrise = 0L,
                    sunset = 0L,
                    pressure = 0,
                    humidity = 0,
                    windSpeed = 0.0,
                    windDirectionInDegree = 0
                )
            }
        )
    }

    override suspend fun getCityWeatherForecastByName(name: String): CityWithWeatherForecastDetailsEntityRel? {
        val city = insertedCities.find { it.name == name } ?: return null
        val cityForecast = insertedForecasts.find { weatherForecastEntity -> weatherForecastEntity.cityId == city.id } ?: return null
        val cityTemperature = insertedTemperatures.find { it.forecastId == cityForecast.id } ?: return null
        val cityCondition = insertedConditions.find { it.forecastId == cityForecast.id } ?: return null

        return CityWithWeatherForecastDetailsEntityRel(
            city = city,
            weatherForecastDetails = listOf(
                WeatherForecastDetailsEntityRel(
                    weatherForecast = cityForecast,
                    temperature = cityTemperature,
                    weatherCondition = cityCondition
                )
            )
        )
    }

    override suspend fun getCityWeatherForecastById(id: Long): CityWithWeatherForecastDetailsEntityRel? {
        val city = insertedCities.find { it.id == id } ?: return null
        val cityForecast = insertedForecasts.find { weatherForecastEntity -> weatherForecastEntity.cityId == city.id } ?: return null
        val cityTemperature = insertedTemperatures.find { it.forecastId == cityForecast.id } ?: return null
        val cityCondition = insertedConditions.find { it.forecastId == cityForecast.id } ?: return null

        return CityWithWeatherForecastDetailsEntityRel(
            city = city,
            weatherForecastDetails = listOf(
                WeatherForecastDetailsEntityRel(
                    weatherForecast = cityForecast,
                    temperature = cityTemperature,
                    weatherCondition = cityCondition
                )
            )
        )
    }

    override suspend fun getLastSearchedCity(): CityWithWeatherForecastDetailsEntityRel? {
        val city = insertedCities.firstOrNull() ?: return null
        val cityForecast = insertedForecasts.find { weatherForecastEntity -> weatherForecastEntity.cityId == city.id } ?: return null
        val cityTemperature = insertedTemperatures.find { it.forecastId == cityForecast.id } ?: return null
        val cityCondition = insertedConditions.find { it.forecastId == cityForecast.id } ?: return null

        return CityWithWeatherForecastDetailsEntityRel(
            city = city,
            weatherForecastDetails = listOf(
                WeatherForecastDetailsEntityRel(
                    weatherForecast = cityForecast,
                    temperature = cityTemperature,
                    weatherCondition = cityCondition
                )
            )
        )
    }


}

