package com.ab.data.source.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.ab.domain.model.entity.TemperatureEntity

interface TemperatureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TemperatureEntity)

}