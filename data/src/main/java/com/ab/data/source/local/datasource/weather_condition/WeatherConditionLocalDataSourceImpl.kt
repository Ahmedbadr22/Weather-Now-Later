package com.ab.data.source.local.datasource.weather_condition

import com.ab.data.source.local.db.dao.WeatherConditionDao
import com.ab.domain.model.entity.WeatherConditionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherConditionLocalDataSourceImpl @Inject constructor(
    private val weatherConditionDao: WeatherConditionDao
): WeatherConditionLocalDataSource {
    override suspend fun insert(entity: WeatherConditionEntity): Long {
        return withContext(Dispatchers.IO) {
            weatherConditionDao.insert(entity)
        }
    }
}