package com.ab.data.source.local.datasource.temperature

import com.ab.domain.model.entity.TemperatureEntity

interface TemperatureLocalDataSource {
    suspend fun insert(entity: TemperatureEntity): Long
}