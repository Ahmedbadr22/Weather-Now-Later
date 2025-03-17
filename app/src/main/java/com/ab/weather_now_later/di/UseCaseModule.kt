package com.ab.weather_now_later.di

import com.ab.core.utils.error.ExceptionHandler
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
        resourceProviderImpl: ResourceProviderImpl
    ): GetLastSearchedCityUseCase {
        return GetLastSearchedCityUseCase(repository, resourceProviderImpl)
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
        resourceProviderImpl: ResourceProviderImpl
    ): GetWeekCityWeatherForecastByIdUseCase {
        return GetWeekCityWeatherForecastByIdUseCase(
            repository,
            exceptionHandler,
            resourceProviderImpl
        )
    }

    @Provides
    @ViewModelScoped
    fun provideGetLastCityOrFetchFromRemoteUseCase(
        repository: WeatherForecastRepository,
        insertWeatherForecastResponseToLocalUseCase: InsertWeatherForecastResponseToLocalUseCase,
        getLastSearchedCityUseCase: GetLastSearchedCityUseCase,
        exceptionHandler: ExceptionHandler,
        resourceProviderImpl: ResourceProviderImpl
    ): GetLastCityOrFetchFromRemoteUseCase {
        return GetLastCityOrFetchFromRemoteUseCase(
            repository,
            getLastSearchedCityUseCase,
            insertWeatherForecastResponseToLocalUseCase,
            exceptionHandler,
            resourceProviderImpl
        )
    }
}