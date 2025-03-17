package com.ab.city_week_forecast

import com.ab.domain.model.model.WeatherForecast

sealed class CityWeekForecastContract {
    data class State(
        val loading: Boolean = false,
        val cityName: String = "",
        val weekForecasts: List<WeatherForecast> = emptyList()
    ): CityWeekForecastContract()

    sealed class Effect: CityWeekForecastContract() {
        data class ShowError(val message: String): Effect()
    }

    sealed class Event: CityWeekForecastContract()
}