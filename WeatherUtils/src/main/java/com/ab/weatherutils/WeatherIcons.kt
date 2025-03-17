package com.ab.weatherutils

import java.util.Locale

object WeatherIcons {

    fun getWeatherIconRes(weatherCondition: String): Int {
        return when (weatherCondition.lowercase(Locale.US)) {
            "clear", "sunny", "sun" -> R.drawable.ic_sunny
            "cloudy", "clouds" -> R.drawable.ic_cloudy
            "rainy", "rain" -> R.drawable.ic_rainy
            "snow" -> R.drawable.ic_snow
            else -> R.drawable.ic_unknown
        }
    }
}
