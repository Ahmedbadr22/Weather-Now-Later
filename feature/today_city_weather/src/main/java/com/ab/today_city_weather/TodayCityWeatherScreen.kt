package com.ab.today_city_weather

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ab.core.ui.theme.WeatherNowLaterTheme
import com.ab.domain.model.model.DayWeatherForecast
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun TodayCityWeatherScreen(
    loading: Boolean,
    message: String?,
    errors: Flow<String>,
    cityName: String,
    dayWeatherForecast: DayWeatherForecast?,
    onCityNameChange: (String) -> Unit,
    onGetWeatherForecast: () -> Unit,
    onNavigateToWeekForecast: (Long) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(errors) {
        errors.collectLatest { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                dayWeatherForecast?.latitude ?: 0.0,
                dayWeatherForecast?.longitude ?: 0.0
            ),
            5f
        )
    }

    LaunchedEffect(dayWeatherForecast) {
        if (dayWeatherForecast != null) {
            cameraPosition.animate(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        dayWeatherForecast.latitude,
                        dayWeatherForecast.longitude
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
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    OutlinedIconButton(
                        onClick = onGetWeatherForecast
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            }
            if (dayWeatherForecast != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = dayWeatherForecast.name,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = dayWeatherForecast.country,
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
                                    dayWeatherForecast.weatherForecast.weatherCondition.main
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
                            currentTemp = dayWeatherForecast.weatherForecast.temperature.nightTemp,
                            minTemp = dayWeatherForecast.weatherForecast.temperature.minTemp,
                            maxTemp = dayWeatherForecast.weatherForecast.temperature.maxTemp,
                            windSpeed = dayWeatherForecast.weatherForecast.windSpeed
                        )
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    Text(
                        text = dayWeatherForecast.weatherForecast.weatherCondition.main,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = dayWeatherForecast.weatherForecast.weatherCondition.description,
                        style = MaterialTheme.typography.displaySmall
                    )
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        onNavigateToWeekForecast(dayWeatherForecast.id)
                    },
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
                                dayWeatherForecast.latitude,
                                dayWeatherForecast.longitude
                            )
                        ),
                        title = dayWeatherForecast.name,
                        snippet = "Capital of ${dayWeatherForecast.country}"
                    )
                }
            } else {
                if (message != null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = message,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
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
            loading = false,
            message = "Message",
            errors = emptyFlow(),
            cityName = "",
            onCityNameChange = {},
            onGetWeatherForecast = {},
            dayWeatherForecast = null,
            onNavigateToWeekForecast = {}
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TodayCityWeatherScreenNightPreview() {
    WeatherNowLaterTheme {
        TodayCityWeatherScreen(
            loading = false,
            errors = emptyFlow(),
            cityName = "",
            message = "",
            onCityNameChange = {},
            onGetWeatherForecast = {},
            dayWeatherForecast = null,
            onNavigateToWeekForecast = {}
        )
    }
}