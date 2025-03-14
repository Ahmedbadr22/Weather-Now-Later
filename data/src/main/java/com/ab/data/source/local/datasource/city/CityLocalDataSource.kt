package com.ab.data.source.local.datasource.city

import com.ab.domain.model.entity.CityEntity

interface CityLocalDataSource {
    suspend fun insert(cityEntity: CityEntity): Long
}