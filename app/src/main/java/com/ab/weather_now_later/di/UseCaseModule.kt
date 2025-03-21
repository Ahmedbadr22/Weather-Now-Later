package com.ab.weather_now_later.di

import com.ab.core.utils.error.ExceptionHandler
import com.ab.core.utils.resource.ResourceProvider
import com.ab.core.utils.resource.ResourceProviderImpl
import com.ab.domain.repository.WeatherForecastRepository
import com.ab.domain.usecases.GetLastCityOrFetchFromRemoteUseCase
import com.ab.domain.usecases.GetLastSearchedCityUseCase
import com.ab.domain.usecases.GetWeekCityWeatherForecastByIdUseCase
import com.ab.domain.usecases.InsertWeatherForecastResponseToLocalUseCase
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
    fun provideGetLastSearchedCityUseCase(
        repository: WeatherForecastRepository,
        resourceProvider: ResourceProvider
    ): GetLastSearchedCityUseCase {
        return GetLastSearchedCityUseCase(repository, resourceProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertWeatherForecastResponseToLocalUseCase(
        repository: WeatherForecastRepository,
    ): InsertWeatherForecastResponseToLocalUseCase {
        return InsertWeatherForecastResponseToLocalUseCase(repository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetWeekCityWeatherForecastByIdUseCase(
        repository: WeatherForecastRepository,
        exceptionHandler: ExceptionHandler,
        resourceProvider: ResourceProvider
    ): GetWeekCityWeatherForecastByIdUseCase {
        return GetWeekCityWeatherForecastByIdUseCase(
            repository,
            exceptionHandler,
            resourceProvider
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetLastCityOrFetchFromRemoteUseCase(
        repository: WeatherForecastRepository,
        insertWeatherForecastResponseToLocalUseCase: InsertWeatherForecastResponseToLocalUseCase,
        getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
        exceptionHandler: ExceptionHandler,
        resourceProvider: ResourceProvider
    ): GetLastCityOrFetchFromRemoteUseCase {
        return GetLastCityOrFetchFromRemoteUseCase(
            repository,
            getLastSearchedCityUseCase,
            insertWeatherForecastResponseToLocalUseCase,
            exceptionHandler,
            resourceProvider
        )
    }
}