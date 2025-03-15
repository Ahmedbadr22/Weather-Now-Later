package com.ab.data.source.local.datasource.city

import com.ab.domain.model.entity.CityEntity

interface CityLocalDataSource {
    suspend fun insert(cityEntity: CityEntity): Long

    suspend fun getAll(): List<CityEntity>

    suspend fun getById(id: Long): CityEntity?

    suspend fun deleteById(id: Long)
}