package com.ab.today_city_weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.core.utils.base.handle
import com.ab.core.utils.resource.ResourceProvider
import com.ab.domain.model.model.TodayWeatherForecast
import com.ab.domain.usecases.FetchWeatherForecastByCityNameUseCase
import com.ab.domain.usecases.GetTodayWeatherForecastByCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayCityWeatherViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val fetchWeatherForecastByCityNameUseCase: FetchWeatherForecastByCityNameUseCase,
    private val getTodayWeatherForecastByCityUseCase: GetTodayWeatherForecastByCityUseCase
): ViewModel() {

    private val _cityName: MutableStateFlow<String> = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    private val _weatherForecast: MutableStateFlow<TodayWeatherForecast?> = MutableStateFlow(null)
    val weatherForecast: StateFlow<TodayWeatherForecast?> = _weatherForecast

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorChannel: Channel<String> = Channel()
    val errorFlow: Flow<String> = _errorChannel.receiveAsFlow()

    fun onClickGetWeatherForecast() {
        val cityName = _cityName.value

        viewModelScope.launch {
            if (cityName.isEmpty()) {
                val failureMsg = resourceProvider.getString(com.ab.core.R.string.this_field_cant_be_empty)
                _errorChannel.send(failureMsg)
                return@launch
            }

            val fetchJob = launch {
                fetchWeatherForecastByCityNameUseCase(_cityName.value)
                    .collectLatest { resource ->
                        resource.handle(
                            onLoading = { loading -> _isLoading.update { loading } },
                            onSuccess = {},
                            onError = { failure -> _errorChannel.send(failure.message) }
                        )
                    }
            }

            fetchJob.join()

            val getTodayWeatherForecastJob = launch {
                getTodayWeatherForecastByCityUseCase(_cityName.value)
                    .collectLatest { resource ->
                        resource.handle(
                            onLoading = {},
                            onSuccess = { data ->
                                _weatherForecast.update { data }
                            },
                            onError = {
                                Log.i("AHMED_BADR", "getTodayWeatherForecastByCityUseCase: Error = $it")
                            }
                        )
                    }
            }

            fetchJob.invokeOnCompletion {
                getTodayWeatherForecastJob.start()
            }
        }
    }

    fun onCityNameChange(cityName: String) {
        _cityName.update { cityName }
    }
}