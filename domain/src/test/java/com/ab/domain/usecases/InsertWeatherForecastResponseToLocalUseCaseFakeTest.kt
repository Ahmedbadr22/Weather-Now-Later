package com.ab.domain.usecases

import com.ab.domain.fake.FakeWeatherForecastRepository
import com.ab.domain.model.dto.CityDto
import com.ab.domain.model.dto.CoordinatesDto
import com.ab.domain.model.dto.TemperatureDto
import com.ab.domain.model.dto.WeatherConditionDto
import com.ab.domain.model.dto.WeatherForecastDto
import com.ab.domain.model.dto.WeatherForecastResponseDto
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals


@ExperimentalCoroutinesApi
class InsertWeatherForecastResponseToLocalUseCaseFakeTest {

    private lateinit var fakeRepository: FakeWeatherForecastRepository
    private lateinit var useCase: InsertWeatherForecastResponseToLocalUseCase

    @Before
    fun setup() {
        fakeRepository = FakeWeatherForecastRepository()
        useCase = InsertWeatherForecastResponseToLocalUseCase(fakeRepository)
    }

    @Test
    fun invoke_validData_InsertAllEntitiesIntoFakeRepositoryCorrectly() = runTest {
        val weatherConditionDto = WeatherConditionDto(
            id = 1,
            main = "Clear",
            description = "Clear sky",
            icon = "01d"
        )

        val forecastDto = WeatherForecastDto(
            dateTimestamp = 1234567890,
            temperature = TemperatureDto(day = 22.5, min = 18.0, max = 25.0, night = 20.0, evening = 22.0, morning = 19.0),
            weather = listOf(weatherConditionDto),
            sunrise = 0L,
            sunset = 0L,
            pressure = 0,
            humidity = 0,
            windSpeed = 0.0,
            windDirectionInDegree = 0,

        )

        val weatherForecastResponse = WeatherForecastResponseDto(
            city = CityDto(
                id = 1,
                name = "Cairo",
                country = "EG",
                coordinates = CoordinatesDto(latitude = 0.0, longitude = 0.0),
                population = 90000000,
                timezone = 12125454
            ),
            statusCode = "200",
            message = 0.0,
            count = 1,
            forecasts = listOf(forecastDto)
        )

        useCase(weatherForecastResponse)

        assertEquals(1, fakeRepository.insertedCities.size)
        assertEquals(1, fakeRepository.insertedForecasts.size)
        assertEquals(1, fakeRepository.insertedTemperatures.size)
        assertEquals(1, fakeRepository.insertedConditions.size)
    }

    @Test
    fun invoke_emptyForecastsList_onlyCityInserted() = runTest {
        val weatherForecastResponse = WeatherForecastResponseDto(
            city = CityDto(
                id = 1,
                name = "Cairo",
                country = "EG",
                coordinates = CoordinatesDto(latitude = 0.0, longitude = 0.0),
                population = 90000000,
                timezone = 12125454
            ),
            statusCode = "200",
            message = 0.0,
            count = 0,
            forecasts = emptyList()
        )

        useCase(weatherForecastResponse)

        assertEquals(1, fakeRepository.insertedCities.size)
        assertEquals(0, fakeRepository.insertedForecasts.size)
        assertEquals(0, fakeRepository.insertedTemperatures.size)
        assertEquals(0, fakeRepository.insertedConditions.size)
    }

    @Test
    fun invoke_forecastWithoutWeatherConditions_insertsCityForecastAndTemperatureCorrectly() = runTest {
        val forecastDto = WeatherForecastDto(
            dateTimestamp = 1234567890,
            temperature = TemperatureDto(day = 22.5, min = 18.0, max = 25.0, night = 20.0, evening = 22.0, morning = 19.0),
            weather = emptyList(),
            sunrise = 0L,
            sunset = 0L,
            pressure = 0,
            humidity = 0,
            windSpeed = 0.0,
            windDirectionInDegree = 0,
        )

        val weatherForecastResponse = WeatherForecastResponseDto(
            city = CityDto(
                id = 1,
                name = "Cairo",
                country = "EG",
                coordinates = CoordinatesDto(latitude = 0.0, longitude = 0.0),
                population = 90000000,
                timezone = 12125454
            ),
            statusCode = "200",
            message = 0.0,
            count = 1,
            forecasts = listOf(forecastDto)
        )

        useCase(weatherForecastResponse)

        assertEquals(1, fakeRepository.insertedCities.size)
        assertEquals(1, fakeRepository.insertedForecasts.size)
        assertEquals(1, fakeRepository.insertedTemperatures.size)
        assertEquals(0, fakeRepository.insertedConditions.size)
    }

    @Test
    fun invoke_forecastWithMultipleWeatherConditions_allEntitiesInsertedCorrectly() = runTest {
        val weatherConditions = listOf(
            WeatherConditionDto(id = 1, main = "Clear", description = "Clear sky", icon = "01d"),
            WeatherConditionDto(id = 2, main = "Clouds", description = "Few clouds", icon = "02d")
        )

        val forecastDto = WeatherForecastDto(
            dateTimestamp = 1234567890,
            temperature = TemperatureDto(day = 22.5, min = 18.0, max = 25.0, night = 20.0, evening = 22.0, morning = 19.0),
            weather = weatherConditions,
            sunrise = 0L,
            sunset = 0L,
            pressure = 0,
            humidity = 0,
            windSpeed = 0.0,
            windDirectionInDegree = 0,
        )

        val weatherForecastResponse = WeatherForecastResponseDto(
            city = CityDto(
                id = 1,
                name = "Cairo",
                country = "EG",
                coordinates = CoordinatesDto(latitude = 0.0, longitude = 0.0),
                population = 90000000,
                timezone = 12125454
            ),
            statusCode = "200",
            message = 0.0,
            count = 1,
            forecasts = listOf(forecastDto)
        )

        useCase(weatherForecastResponse)

        assertEquals(1, fakeRepository.insertedCities.size)
        assertEquals(1, fakeRepository.insertedForecasts.size)
        assertEquals(1, fakeRepository.insertedTemperatures.size)
        assertEquals(2, fakeRepository.insertedConditions.size)
    }


}
