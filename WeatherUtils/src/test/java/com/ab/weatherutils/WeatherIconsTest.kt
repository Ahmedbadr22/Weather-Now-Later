package com.ab.weatherutils

import org.junit.Test

import org.junit.Assert.*


class WeatherIconsTest {

    @Test
    fun getWeatherIconRes_validWeatherCondition_returnsCorrectIconResource() {
        val iconRes = WeatherIcons.getWeatherIconRes("sunny")
        assertEquals(R.drawable.ic_sunny, iconRes)
    }

    @Test
    fun getWeatherIconRes_invalidWeatherCondition_returnsUnknownIconResource() {
        val iconRes = WeatherIcons.getWeatherIconRes("dsfsfs")
        assertEquals(R.drawable.ic_unknown, iconRes)
    }
}