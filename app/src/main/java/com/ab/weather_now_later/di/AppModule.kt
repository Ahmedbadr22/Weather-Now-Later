package com.ab.weather_now_later.di

import android.content.Context
import com.ab.core.utils.error.ExceptionHandler
import com.ab.core.utils.resource.ResourceProvider
import com.ab.core.utils.resource.ResourceProviderImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return ResourceProviderImpl(context)
    }

    @Provides
    @Singleton
    fun provideExceptionHandler(
        gson: Gson,
        resourceProviderImpl: ResourceProviderImpl
    ): ExceptionHandler = ExceptionHandler(gson, resourceProviderImpl)
}