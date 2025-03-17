package com.ab.domain

import com.ab.core.utils.resource.ResourceProvider

class FakeResourceProvider : ResourceProvider {
    override fun getString(resId: Int, vararg formatArgs: Any): String {
        val message = when (resId) {
            com.ab.core.R.string.no_city_found_with_this_name -> "No city found"
            com.ab.core.R.string.no_weather_forecast_today -> "No forecast for today"
            else -> "Unknown"
        }

        // Apply formatting if there are formatArgs
        return if (formatArgs.isNotEmpty()) {
            String.format(message, *formatArgs)
        } else {
            message
        }
    }
}
