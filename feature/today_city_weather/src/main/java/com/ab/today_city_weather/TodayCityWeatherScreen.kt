package com.ab.today_city_weather

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ab.core.ui.theme.WeatherNowLaterTheme

@Composable
fun TodayCityWeatherScreen(
    cityName: String,
    onCityNameChange: (String) -> Unit,
    onGetWeatherForecast: () -> Unit
) {

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = cityName,
                onValueChange = onCityNameChange
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                onClick = onGetWeatherForecast
            ) {
                Text("Click")
            }
        }
    }
}

@Preview
@Composable
private fun TodayCityWeatherScreenDayPreview() {
    WeatherNowLaterTheme {
        TodayCityWeatherScreen(
            cityName = "",
            onCityNameChange = {},
            onGetWeatherForecast = {}
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TodayCityWeatherScreenNightPreview() {
    WeatherNowLaterTheme {
        TodayCityWeatherScreen(
            cityName = "",
            onCityNameChange = {},
            onGetWeatherForecast = {}
        )
    }
}