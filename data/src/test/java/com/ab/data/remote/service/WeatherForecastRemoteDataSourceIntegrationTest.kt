package com.ab.data.remote.service

import com.ab.core.utils.network.AuthInterceptor
import com.ab.data.source.remote.service.WeatherForecastClientService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class WeatherForecastRemoteDataSourceIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: WeatherForecastClientService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherForecastClientService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fetchWeatherNDaysForecastByCity_returnsExpectedData() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(oneDayWeatherForecastCairoJson)
            .setResponseCode(HttpURLConnection.HTTP_OK)

        mockWebServer.enqueue(mockResponse)

        val result = apiService.fetchWeatherNDaysForecastByCity("Cairo", "metric", 1)

        assertEquals("Cairo", result.city.name)
        assertEquals(1, result.count)
    }

    @Test
    fun fetchWeatherNDaysForecastByCity_emptyCityName_throwsHttpException(): Unit = runBlocking {
        val mockResponse = MockResponse()
            .setBody(oneDayWeatherForecastCairoJson)
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)

        mockWebServer.enqueue(mockResponse)

        assertThrows<HttpException> {
            apiService.fetchWeatherNDaysForecastByCity("", "metric", 1)
        }
    }
}