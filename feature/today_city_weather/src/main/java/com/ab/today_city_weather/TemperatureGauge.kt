package com.ab.today_city_weather

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlin.math.*

@Composable
fun TemperatureGauge(
    currentTemp: Int = 26,
    minTemp: Int = 9,
    maxTemp: Int = 32,
    windSpeed: String = "20 mph"
) {
    val sweepAngle = 240f // Arc span (starting from -120° to +120°)
    val startAngle = 150f // Start from left
    val indicatorAngle = startAngle + ((currentTemp - minTemp).toFloat() / (maxTemp - minTemp) * sweepAngle)

    val onBackgroundColor = MaterialTheme.colorScheme.onBackground

    Box(modifier = Modifier.size(140.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 8.dp.toPx()
            // Draw Background Arc
            drawArc(
                color = Color.Gray.copy(alpha = 0.3f),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

            // Draw Filled Arc (Progress)
            drawArc(
                color = onBackgroundColor,
                startAngle = startAngle,
                sweepAngle = (indicatorAngle - startAngle),
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )

        }

        // Temperature Text
        Text(
            text = "$currentTemp°",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        // Wind Speed Text
        Text(
            text = windSpeed,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            modifier = Modifier.align(Alignment.Center).offset(y = 30.dp)
        )

        // Min Temp Text
        Text(
            text = "$minTemp°",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.BottomStart).offset(x = 16.dp)
        )

        // Max Temp Text
        Text(
            text = "$maxTemp°",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.BottomEnd).offset(x = (-16).dp)
        )
    }
}
