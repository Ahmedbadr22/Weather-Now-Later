package com.ab.domain

import com.ab.core.utils.base.Resource
import com.ab.domain.usecases.GetLastSearchedCityUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@ExperimentalCoroutinesApi
class GetLastSearchedCityUseCaseFakeTest {
    private lateinit var fakeRepository: FakeWeatherForecastRepository
    private lateinit var useCase: GetLastSearchedCityUseCase
    private lateinit var fakeResourceProvider: FakeResourceProvider

    @Before
    fun setup() {
        fakeRepository = FakeWeatherForecastRepository()
        fakeResourceProvider = FakeResourceProvider()
        useCase = GetLastSearchedCityUseCase(fakeRepository, fakeResourceProvider)
    }

    @Test
    fun invoke_cityAndForecastAvailable_returnsSuccess() = runTest {
        val cityEntity = FakeTestData.buildCityEntity(id = 1, name = "Cairo")
        fakeRepository.insertCityToLocal(cityEntity)

        val forecast = FakeTestData.buildForecastEntity(cityId = 1, today = true)
        val temp = FakeTestData.buildTemperatureEntity(forecastId = forecast.id)
        val condition = FakeTestData.buildConditionEntity(forecastId = forecast.id)

        fakeRepository.insertWeatherForecastToLocal(forecast)
        fakeRepository.insertTemperatureToLocal(temp)
        fakeRepository.insertWeatherConditionToLocal(condition)

        val result = useCase()

        assertTrue(result is Resource.Success)
        assertEquals("Cairo", (result as Resource.Success).data.name)
    }

    @Test
    fun invoke_noLastSearchedCity_returnsFailure() = runTest {
        val result = useCase()

        assertTrue(result is Resource.Fail)
        assertEquals("No city found", (result as Resource.Fail).failure.message)
    }

    @Test
    fun invoke_noForecastForToday_returnsFailure() = runTest {
        val cityEntity = FakeTestData.buildCityEntity(id = 1, name = "Alexandria")
        fakeRepository.insertedCities.add(cityEntity)

        val forecast = FakeTestData.buildForecastEntity(cityId = 1, today = false)
        val temp = FakeTestData.buildTemperatureEntity(forecastId = forecast.id)
        val condition = FakeTestData.buildConditionEntity(forecastId = forecast.id)
        fakeRepository.insertedForecasts.add(forecast)
        fakeRepository.insertedTemperatures.add(temp)
        fakeRepository.insertedConditions.add(condition)

        val result = useCase()

        assertTrue(result is Resource.Fail)
        assertEquals("No forecast for today", (result as Resource.Fail).failure.message)
    }
}
