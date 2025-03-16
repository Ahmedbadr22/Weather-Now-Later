package com.ab.today_city_weather

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ab.core.ui.theme.WeatherNowLaterTheme
import com.ab.domain.model.model.TodayWeatherForecast
import com.ab.weatherutils.TemperatureGauge
import com.ab.weatherutils.WeatherIcons
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun TodayCityWeatherScreen(
    cityName: String,
    todayWeatherForecast: TodayWeatherForecast?,
    onCityNameChange: (String) -> Unit,
    onGetWeatherForecast: () -> Unit
) {
    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                todayWeatherForecast?.latitude ?: 0.0,
                todayWeatherForecast?.longitude ?: 0.0
            ),
            5f
        )
    }

    LaunchedEffect(todayWeatherForecast) {
        if (todayWeatherForecast != null) {
            cameraPosition.animate(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        todayWeatherForecast.latitude,
                        todayWeatherForecast.longitude
                    )
                ),
                700
            )
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 16.dp)
                .fillMaxSize(),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = cityName,
                    onValueChange = onCityNameChange
                )
                OutlinedIconButton(
                    onClick = onGetWeatherForecast
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
            if (todayWeatherForecast != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = todayWeatherForecast.name,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = todayWeatherForecast.country,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(140.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .size(100.dp),
                            painter = painterResource(
                                WeatherIcons.getWeatherIconRes(
                                    todayWeatherForecast.weatherForecast.weatherCondition.main
                                )
                            ),
                            contentDescription = null
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        TemperatureGauge(
                            currentTemp = todayWeatherForecast.weatherForecast.temperature.nightTemp,
                            minTemp = todayWeatherForecast.weatherForecast.temperature.minTemp,
                            maxTemp = todayWeatherForecast.weatherForecast.temperature.maxTemp,
                            windSpeed = todayWeatherForecast.weatherForecast.windSpeed
                        )
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(
                        text = todayWeatherForecast.weatherForecast.weatherCondition.main,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = todayWeatherForecast.weatherForecast.weatherCondition.description,
                        style = MaterialTheme.typography.displaySmall
                    )
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {},
                    shape = RoundedCornerShape(5),
                ) {
                    Text(stringResource(R.string.show_7_day_forecast))
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPosition,
                    properties = MapProperties(isTrafficEnabled = false, isBuildingEnabled = false)
                ) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                todayWeatherForecast.latitude,
                                todayWeatherForecast.longitude
                            )
                        ),
                        title = todayWeatherForecast.name,
                        snippet = "Capital of ${todayWeatherForecast.country}"
                    )
                }
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
            onGetWeatherForecast = {},
            todayWeatherForecast = null
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
            onGetWeatherForecast = {},
            todayWeatherForecast = null
        )
    }
}