package com.ab.data.remote.source

import com.ab.data.source.remote.datasource.WeatherForecastRemoteDataSourceImpl
import com.ab.data.source.remote.service.WeatherForecastClientService
import com.ab.domain.model.dto.CityDto
import com.ab.domain.model.dto.CoordinatesDto
import com.ab.domain.model.dto.TemperatureDto
import com.ab.domain.model.dto.WeatherConditionDto
import com.ab.domain.model.dto.WeatherForecastDto
import com.ab.domain.model.dto.WeatherForecastResponseDto
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class WeatherForecastRemoteDataSourceImplTest {

    private val mockClientService: WeatherForecastClientService = mock(WeatherForecastClientService::class.java)
    private val remoteDataSource = WeatherForecastRemoteDataSourceImpl(mockClientService)

    @Test
    fun fetchWeatherNDaysForecastByCity_callsClientService_returnsExpectedData() = runTest {
        // Arrange
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
            forecasts = listOf(
                WeatherForecastDto(
                    dateTimestamp = 1741946400,
                    sunrise = 1741925167,
                    sunset = 1741968132,
                    temperature = TemperatureDto(
                        day = 29.96,
                        min = 19.02,
                        max = 31.52,
                        night = 20.38,
                        evening = 30.11,
                        morning = 19.02
                    ),
                    pressure = 1016,
                    humidity = 16,
                    windSpeed = 7.48,
                    windDirectionInDegree = 43,
                    weather = listOf(
                        WeatherConditionDto(
                            id = 800,
                            main = "Clear",
                            description = "sky is clear",
                            icon = "01d"
                        )
                    )
                )
            )
        )

        `when`(mockClientService.fetchWeatherNDaysForecastByCity("Cairo", "metric", 1))
            .thenReturn(expectedResponse)

        // Act
        val result = remoteDataSource.fetchWeatherNDaysForecastByCity("Cairo", 1)

        // Assert
        verify(mockClientService).fetchWeatherNDaysForecastByCity("Cairo", "metric", 1)
        assertEquals(expectedResponse, result)
    }
}
