package com.ab.today_city_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.core.R
import com.ab.core.utils.base.handle
import com.ab.core.utils.error.Failure
import com.ab.core.utils.resource.ResourceProvider
import com.ab.domain.model.model.DayWeatherForecast
import com.ab.domain.usecases.GetLastCityOrFetchFromRemoteUseCase
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
    private val getLastCityOrFetchFromRemoteUseCase: GetLastCityOrFetchFromRemoteUseCase
): ViewModel() {

    private val _cityName: MutableStateFlow<String> = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    private val _weatherForecast: MutableStateFlow<DayWeatherForecast?> = MutableStateFlow(null)
    val weatherForecast: StateFlow<DayWeatherForecast?> = _weatherForecast

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorChannel: Channel<String> = Channel()
    val errorFlow: Flow<String> = _errorChannel.receiveAsFlow()

    private val _message: MutableStateFlow<String?> = MutableStateFlow(null)
    val message: StateFlow<String?> = _message

    init {
        viewModelScope.launch {
            getLastCityOrFetchFromRemoteUseCase()
                .collectLatest { resource ->
                    resource.handle(
                        onLoading = { loading -> _isLoading.update { loading } },
                        onSuccess = { forecast ->
                            _weatherForecast.update { forecast }
                        },
                        onError = { failure ->
                            if (failure is Failure.NotFound) {
                                _message.update { "Please type city name to view" }
                                return@handle
                            }
                            _errorChannel.send(failure.message)
                        }
                    )
                }
        }
    }

    fun onClickGetWeatherForecast() {
        val cityName = _cityName.value

        if (cityName.isEmpty()) {
            val failureMsg = resourceProvider.getString(R.string.this_field_cant_be_empty)
            viewModelScope.launch { _errorChannel.send(failureMsg) }
            return
        }

        viewModelScope.launch {
            getLastCityOrFetchFromRemoteUseCase(cityName)
                .collectLatest { resource ->
                    resource.handle(
                        onLoading = { loading -> _isLoading.update { loading } },
                        onSuccess = { forecast ->
                            _weatherForecast.update { forecast }
                        },
                        onError = { failure ->
                            _errorChannel.send(failure.message)
                        }
                    )
                }
        }
    }

    fun onCityNameChange(cityName: String) {
        _cityName.update { cityName }
    }
}