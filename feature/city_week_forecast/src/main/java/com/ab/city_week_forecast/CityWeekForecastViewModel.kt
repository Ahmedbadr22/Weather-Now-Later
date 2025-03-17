package com.ab.city_week_forecast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ab.core.utils.base.handle
import com.ab.domain.usecases.GetWeekCityWeatherForecastByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeekForecastViewModel @Inject constructor(
    private val getWeekCityWeatherForecastByIdUseCase: GetWeekCityWeatherForecastByIdUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val route = savedStateHandle.toRoute<CityWeekForecastScreenRoute>()

    private val _uiState: MutableStateFlow<CityWeekForecastContract.State> =
        MutableStateFlow(CityWeekForecastContract.State())
    val uiState: StateFlow<CityWeekForecastContract.State> = _uiState

    private val _sideEffects: Channel<CityWeekForecastContract.Effect> = Channel()
    val sideEffects = _sideEffects.receiveAsFlow()


    init {
        viewModelScope.launch {
            getWeekCityWeatherForecastByIdUseCase(route.cityId)
                .collectLatest { resource ->
                    resource.handle(
                        onLoading = { isLoading ->
                            _uiState.update { it.copy(loading = isLoading) }
                        },
                        onSuccess = { weatherForecast ->
                            _uiState.update { state ->
                                state.copy(
                                    cityName = weatherForecast.name,
                                    weekForecasts = weatherForecast.weatherForecasts
                                )
                            }
                        },
                        onError = { failure ->
                            _sideEffects.send(CityWeekForecastContract.Effect.ShowError(failure.message))
                        }
                    )
                }
        }
    }

}