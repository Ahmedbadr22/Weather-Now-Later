package com.ab.data.interceptor

import com.ab.core.BuildConfig
import com.ab.core.utils.network.AuthInterceptor
import com.ab.data.source.remote.service.WeatherForecastClientService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AuthInterceptorTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: WeatherForecastClientService

    companion object {
        private const val API_KEY_QUERY = "appid"
    }

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `AuthInterceptor adds APP_ID to request when annotated`() = runBlocking {
        // Mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{}") // Empty JSON response
        mockWebServer.enqueue(mockResponse)

        // Execute API call
        service.fetchWeatherNDaysForecastByCity(
            cityName = "Cairo",
            unit = "metric",
            dayCount = 5
        )

        // Capture the request
        val request = mockWebServer.takeRequest()

        // Verify that the request URL contains the APP_ID query parameter
        val url = request.requestUrl.toString()
        assertTrue("APP_ID parameter is missing", url.contains("$API_KEY_QUERY=${BuildConfig.APP_KEY}"))
    }
}
