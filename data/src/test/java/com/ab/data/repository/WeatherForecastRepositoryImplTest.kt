package com.ab.data.repository

import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSource
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSource
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSource
import com.ab.data.source.remote.datasource.WeatherForecastRemoteDataSource
import com.ab.domain.model.dto.CityDto
import com.ab.domain.model.dto.CoordinatesDto
import com.ab.domain.model.dto.WeatherForecastResponseDto
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherForecastRepositoryImplTest {

    private val weatherForecastRemoteDataSource: WeatherForecastRemoteDataSource = mockk()
    private val weatherForecastLocalDataSource: WeatherForecastLocalDataSource = mockk()
    private val cityLocalDataSource: CityLocalDataSource = mockk()
    private val temperatureLocalDataSource: TemperatureLocalDataSource = mockk()
    private val weatherConditionLocalDataSource: WeatherConditionLocalDataSource = mockk()

    private lateinit var repository: WeatherForecastRepositoryImpl

    @Before
    fun setUp() {
        repository = WeatherForecastRepositoryImpl(
            weatherForecastRemoteDataSource,
            weatherForecastLocalDataSource,
            cityLocalDataSource,
            temperatureLocalDataSource,
            weatherConditionLocalDataSource
        )
    }

    @Test
    fun fetchWeatherForecastByCityFromRemote_returnsWeatherForecastResponseDto() = runTest {
        // Arrange
        val cityName = "Cairo"
        val dayCount = 5
        val expectedResponse = WeatherForecastResponseDto(
            city = CityDto(
                country = "EG",
                id = 360630,
                name = "Cairo",
                coordinates = CoordinatesDto(
                    latitude = 30.1397,
                    longitude = 31.2781
                ),
                population = 7734614,
                timezone = 7200
            ),
            statusCode = "200",
            message = 1.7146192,
            count = 1,
            forecasts = emptyList()
        )
        coEvery { weatherForecastRemoteDataSource.fetchWeatherNDaysForecastByCity(cityName, dayCount) } returns expectedResponse

        val result = repository.fetchWeatherForecastByCityFromRemote(cityName, dayCount)

        assertEquals(expectedResponse, result)
        coVerify { weatherForecastRemoteDataSource.fetchWeatherNDaysForecastByCity(cityName, dayCount) }
    }

    @Test
    fun insertCityToLocal_calls_cityLocalDataSource() = runTest {
        val city = CityEntity(
            id = 1,
            name = "Cairo",
            country = "EG",
            population = 7734614,
            timezone = 7200,
            latitude = 30.1397,
            longitude = 31.2781
        )
        coEvery { cityLocalDataSource.insert(city) } returns 1L

        val result = repository.insertCityToLocal(city)

        assertEquals(1L, result)
        coVerify { cityLocalDataSource.insert(city) }
    }

    @Test
    fun insertWeatherForecastToLocal_calls_weatherForecastLocalDataSource() = runTest {
        val forecast = WeatherForecastEntity(
            id = 1,
            dateTimestamp = 1741946400,
            sunrise = 1741925167,
            sunset = 1741968132,
            cityId = 1,
            pressure = 1016,
            humidity = 16,
            windSpeed = 7.48,
            windDirectionInDegree = 43
        )
        coEvery { weatherForecastLocalDataSource.insert(forecast) } returns 1L

        val result = repository.insertWeatherForecastToLocal(forecast)

        assertEquals(1L, result)
        coVerify { weatherForecastLocalDataSource.insert(forecast) }
    }

    @Test
    fun getLastSearchedCity_calls_cityLocalDataSource() = runTest {
        val mockResponse = CityWithWeatherForecastDetailsEntityRel(
            city = CityEntity(
                id = 1,
                name = "Cairo",
                country = "EG",
                population = 7734614,
                timezone = 7200,
                latitude = 30.1397,
                longitude = 31.2781
            ),
            weatherForecastDetails = emptyList()
        )
        coEvery { cityLocalDataSource.getLastSearchedCity() } returns mockResponse

        val result = repository.getLastSearchedCity()

        assertEquals(mockResponse, result)
        coVerify { cityLocalDataSource.getLastSearchedCity() }
    }
}
