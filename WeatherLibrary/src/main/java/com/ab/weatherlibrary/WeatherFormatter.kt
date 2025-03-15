package com.ab.weatherlibrary

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherFormatter {

    fun formatTemperature(celsius: Double): String {
        return String.format(Locale.US, "%.1f°C", celsius)
    }

    fun formatWindSpeed(speed: Double): String {
        return String.format(Locale.US, "%.1f km/h", speed)
    }

    fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.US)
        return dateFormat.format(Date(timestamp * 1000))
    }
}
