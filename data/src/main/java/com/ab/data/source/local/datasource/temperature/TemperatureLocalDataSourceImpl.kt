package com.ab.data.source.local.datasource.temperature

import com.ab.data.source.local.db.dao.TemperatureDao
import com.ab.domain.model.entity.TemperatureEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TemperatureLocalDataSourceImpl @Inject constructor(
    private val temperatureDao: TemperatureDao
): TemperatureLocalDataSource {

    override suspend fun insert(entity: TemperatureEntity): Long {
        return withContext(Dispatchers.IO) {
            temperatureDao.insert(entity)
        }
    }
}