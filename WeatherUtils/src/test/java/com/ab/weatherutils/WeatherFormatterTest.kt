package com.ab.weatherutils

import org.junit.Test

import org.junit.Assert.*


class WeatherFormatterTest {

    @Test
    fun formatDate_validTimestamp_returnsFormattedDate() {
        val formattedDate = WeatherFormatter.formatDate(1742205600)
        assertEquals("Mon, 17 Mar 2025", formattedDate)
    }

    @Test
    fun formatTemperature_validCelsius_returnsFormattedTemperature() {
        val formattedTemperature = WeatherFormatter.formatTemperature(25.0)
        assertEquals("25.0°C", formattedTemperature)
    }

    @Test
    fun formatWindSpeed_validSpeedValue_returnsFormattedWindSpeed() {
        val formattedWindSpeed = WeatherFormatter.formatWindSpeed(10.0)
        assertEquals("10.0 km/h", formattedWindSpeed)
    }

}