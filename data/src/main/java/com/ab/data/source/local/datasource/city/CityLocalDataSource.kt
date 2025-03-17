package com.ab.data.source.local.datasource.city

import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel

interface CityLocalDataSource {
    suspend fun insert(cityEntity: CityEntity): Long

    suspend fun getAll(): List<CityEntity>

    suspend fun getAllWithRelations(): List<CityWithWeatherForecastDetailsEntityRel>

    suspend fun getCityWeatherForecastByName(name: String): CityWithWeatherForecastDetailsEntityRel?

    suspend fun getCityWeatherForecastById(id: Long): CityWithWeatherForecastDetailsEntityRel?

    suspend fun getLastSearchedCity(): CityWithWeatherForecastDetailsEntityRel?

    suspend fun getById(id: Long): CityEntity?

    suspend fun deleteById(id: Long)
}