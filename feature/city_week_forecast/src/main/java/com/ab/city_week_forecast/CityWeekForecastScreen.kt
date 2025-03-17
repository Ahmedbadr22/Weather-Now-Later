package com.ab.city_week_forecast

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.ab.weatherutils.R
import com.ab.weatherutils.WeatherFormatter
import com.ab.weatherutils.WeatherIcons
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityWeekForecastScreen(
    uiState: CityWeekForecastContract.State,
    sideEffects: Flow<CityWeekForecastContract.Effect>,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(sideEffects) {
        sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is CityWeekForecastContract.Effect.ShowError -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.cityName,
                        style = MaterialTheme.typography.displaySmall
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = navigateBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = stringResource(com.ab.city_week_forecast.R.string.navigate_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                uiState.weekForecasts,
                key = { item -> item.id }
            ) { item ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    text = WeatherFormatter.formatDate(item.dateTimestamp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    text = item.weatherCondition.main,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(item.weatherCondition.description)
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    modifier = Modifier.size(70.dp),
                                    painter = painterResource(WeatherIcons.getWeatherIconRes(item.weatherCondition.main)),
                                    contentDescription = item.weatherCondition.main
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherConditionDetailIconWithLabel(
                                temperature = item.temperature.dayTemp,
                                iconPainter = painterResource(R.drawable.ic_sunny)
                            )
                            WeatherConditionDetailIconWithLabel(
                                temperature = item.temperature.nightTemp,
                                iconPainter = painterResource(R.drawable.ic_night)
                            )
                            WeatherConditionDetailIconWithLabel(
                                temperature = item.temperature.maxTemp,
                                iconPainter = painterResource(R.drawable.ic_max_temp)
                            )

                            WeatherConditionDetailIconWithLabel(
                                temperature = item.temperature.minTemp,
                                iconPainter = painterResource(R.drawable.ic_min_temp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun CityWeekForecastScreenDayPreview() {
    WeatherNowLaterTheme {
        CityWeekForecastScreen(
            uiState = CityWeekForecastContract.State(
                loading = false,
                cityName = "Cairo",
                weekForecasts = emptyList()
            ),
            sideEffects = emptyFlow(),
            navigateBack = {}
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CityWeekForecastScreenNightPreview() {
    WeatherNowLaterTheme {
        CityWeekForecastScreen(
            uiState = CityWeekForecastContract.State(
                loading = false,
                cityName = "Cairo",
                weekForecasts = emptyList()
            ),
            sideEffects = emptyFlow(),
            navigateBack = {}
        )
    }
}