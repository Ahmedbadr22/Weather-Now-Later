package com.ab.data.source.local.datasource.weather_forecast

import com.ab.data.source.local.db.dao.WeatherForecastDao
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherForecastLocalDataSourceImpl @Inject constructor(
    private val weatherForecastDao: WeatherForecastDao
): WeatherForecastLocalDataSource {
    override suspend fun insert(entity: WeatherForecastEntity): Long {
        return withContext(Dispatchers.IO) {
            weatherForecastDao.insert(entity)
        }
    }
}