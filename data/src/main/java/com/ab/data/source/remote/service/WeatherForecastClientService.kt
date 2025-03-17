package com.ab.data.source.remote.service

import com.ab.core.utils.constants.Endpoint.CITY_NAME_QUERY
import com.ab.core.utils.constants.Endpoint.DAY_COUNT_QUERY
import com.ab.core.utils.constants.Endpoint.GET_CITY_WEATHER_N_DAY_FORECAST_URL
import com.ab.core.utils.constants.Endpoint.UNITS_QUERY
import com.ab.core.utils.network.AuthenticateWithAppId
import com.ab.domain.model.dto.WeatherForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastClientService {
    @AuthenticateWithAppId
    @GET(GET_CITY_WEATHER_N_DAY_FORECAST_URL)
    suspend fun fetchWeatherNDaysForecastByCity(
        @Query(CITY_NAME_QUERY) cityName: String,
        @Query(UNITS_QUERY) unit: String = "metric",
        @Query(DAY_COUNT_QUERY) dayCount: Int,
    ): WeatherForecastResponseDto
}