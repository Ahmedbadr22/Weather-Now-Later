package com.ab.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ab.core.utils.constants.DB.TEMPERATURE_TABLE_NAME
import com.ab.domain.model.entity.TemperatureEntity

@Dao
interface TemperatureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: TemperatureEntity): Long

    @Query("SELECT * FROM $TEMPERATURE_TABLE_NAME")
    suspend fun getAll(): List<TemperatureEntity>

    @Query("SELECT * FROM $TEMPERATURE_TABLE_NAME WHERE id = :id")
    suspend fun getById(id: Long): TemperatureEntity?

}