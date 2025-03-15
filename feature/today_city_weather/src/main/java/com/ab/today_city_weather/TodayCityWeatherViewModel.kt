package com.ab.today_city_weather

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.core.utils.base.handle
import com.ab.domain.usecases.FetchWeatherForecastByCityNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayCityWeatherViewModel @Inject constructor(
    private val fetchWeatherForecastByCityNameUseCase: FetchWeatherForecastByCityNameUseCase
): ViewModel() {

    private val _cityName: MutableStateFlow<String> = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    fun onClickGetWeatherForecast() {
        viewModelScope.launch {
            fetchWeatherForecastByCityNameUseCase(_cityName.value)
                .collectLatest { resource ->
                    resource.handle(
                        onLoading = {
                            Log.i("AHMED_BADR", "onClickGetWeatherForecast: Loading")
                        },
                        onSuccess = {
                            Log.i("AHMED_BADR", "onClickGetWeatherForecast: Success")
                        },
                        onError = {
                            Log.i("AHMED_BADR", "onClickGetWeatherForecast: Error = $it")
                        }
                    )
                }
        }
    }
}