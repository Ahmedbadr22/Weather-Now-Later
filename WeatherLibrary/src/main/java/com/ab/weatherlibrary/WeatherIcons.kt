package com.ab.weatherlibrary

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import java.util.Locale

object WeatherIcons {

    fun getWeatherIcon(context: Context, weatherCondition: String): Drawable? {
        return when (weatherCondition.lowercase(Locale.US)) {
            "clear" -> ContextCompat.getDrawable(context, R.drawable.ic_sunny)
            "cloudy" -> ContextCompat.getDrawable(context, R.drawable.ic_cloudy)
            "rainy" -> ContextCompat.getDrawable(context, R.drawable.ic_rainy)
            else -> ContextCompat.getDrawable(context, R.drawable.ic_unknown)
        }
    }
}
