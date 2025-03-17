package com.ab.domain.usecases

import com.ab.core.utils.base.Resource
import com.ab.core.utils.error.ExceptionHandler
import com.ab.domain.fake.FakeResourceProvider
import com.ab.domain.fake.FakeTestData
import com.ab.domain.fake.FakeWeatherForecastRepository
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

@ExperimentalCoroutinesApi
class GetWeekCityWeatherForecastByIdUseCaseFakeTest {

    private lateinit var fakeRepository: FakeWeatherForecastRepository
    private lateinit var useCase: GetWeekCityWeatherForecastByIdUseCase

    private lateinit var fakeResourceProvider: FakeResourceProvider

    @Before
    fun setup() {
        fakeRepository = FakeWeatherForecastRepository()
        fakeResourceProvider = FakeResourceProvider()
        val exceptionHandler = ExceptionHandler(gson = Gson(), fakeResourceProvider)
        useCase = GetWeekCityWeatherForecastByIdUseCase(fakeRepository, exceptionHandler, fakeResourceProvider)
    }

    @Test
    fun invoke_validCityId_returnsSuccess() = runTest {
        val cityEntity = FakeTestData.buildCityEntity(id = 1, name = "Cairo")
        fakeRepository.insertCityToLocal(cityEntity)

        val forecast = FakeTestData.buildForecastEntity(cityId = 1, today = true)
        val forecast2 = FakeTestData.buildForecastEntity(cityId = 1, today = true)
        val temp = FakeTestData.buildTemperatureEntity(forecastId = forecast.id)
        val condition = FakeTestData.buildConditionEntity(forecastId = forecast.id)

        fakeRepository.insertWeatherForecastToLocal(forecast)
        fakeRepository.insertWeatherForecastToLocal(forecast2)
        fakeRepository.insertTemperatureToLocal(temp)
        fakeRepository.insertWeatherConditionToLocal(condition)


        val flow = useCase(1)
        val firstEmit = flow.first()
        val secondEmit = flow.last()

        assertTrue(firstEmit is Resource.Loading)
        assertTrue(secondEmit is Resource.Success)
        assertEquals("Cairo", (secondEmit as Resource.Success).data.name)
    }

    @Test
    fun invoke_cityNotFound_returnsFailure() = runTest {
        val flow = useCase(1)
        val firstEmit = flow.first()
        val secondEmit = flow.last()

        assertTrue(firstEmit is Resource.Loading)
        assertTrue(secondEmit is Resource.Fail)
        val failure = secondEmit as Resource.Fail
        assertTrue(failure.failure is com.ab.core.utils.error.Failure.NotFound)
    }
}
