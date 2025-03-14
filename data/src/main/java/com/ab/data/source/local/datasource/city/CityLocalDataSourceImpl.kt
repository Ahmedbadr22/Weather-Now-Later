package com.ab.data.source.local.datasource.city

import com.ab.data.source.local.db.dao.CityDao
import com.ab.domain.model.entity.CityEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CityLocalDataSourceImpl @Inject constructor (
    private val cityDao: CityDao
): CityLocalDataSource {

    override suspend fun insert(cityEntity: CityEntity): Long {
        return withContext(Dispatchers.IO) {
            cityDao.insert(cityEntity)
        }
    }
}