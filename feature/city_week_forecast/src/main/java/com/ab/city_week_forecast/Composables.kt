package com.ab.city_week_forecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ab.domain.model.model.WeatherCondition
import com.ab.weatherutils.R
import com.ab.weatherutils.WeatherFormatter

@Composable
fun WeatherConditionDetailIconWithLabel(
    modifier: Modifier = Modifier,
    temperature: Double,
    iconPainter: Painter,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = iconPainter,
            contentDescription = null
        )
        Text(WeatherFormatter.formatTemperature(temperature))
    }
}