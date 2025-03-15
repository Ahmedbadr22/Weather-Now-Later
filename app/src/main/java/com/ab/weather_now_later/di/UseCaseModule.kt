package com.ab.weather_now_later.di

import com.ab.core.utils.error.ExceptionHandler
import com.ab.core.utils.resource.ResourceProvider
import com.ab.domain.repository.WeatherForecastRepository
import com.ab.domain.usecases.FetchWeatherForecastByCityNameUseCase
import com.ab.domain.usecases.GetTodayWeatherForecastByCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideFetchWeatherForecastByCityUseCase(
        repository: WeatherForecastRepository,
        exceptionHandler: ExceptionHandler
    ): FetchWeatherForecastByCityNameUseCase {
        return FetchWeatherForecastByCityNameUseCase(repository, exceptionHandler)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTodayWeatherForecastByCityUseCase(
        repository: WeatherForecastRepository,
        exceptionHandler: ExceptionHandler,
        resourceProvider: ResourceProvider
    ): GetTodayWeatherForecastByCityUseCase {
        return GetTodayWeatherForecastByCityUseCase(repository, resourceProvider, exceptionHandler)
    }
}