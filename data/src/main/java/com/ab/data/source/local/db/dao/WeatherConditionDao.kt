package com.ab.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ab.core.utils.constants.DB.WEATHER_CONDITION_TABLE_NAME
import com.ab.domain.model.entity.WeatherConditionEntity

@Dao
interface WeatherConditionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeatherConditionEntity): Long

    @Query("SELECT * FROM $WEATHER_CONDITION_TABLE_NAME")
    suspend fun getAll(): List<WeatherConditionEntity>

    @Query("SELECT * FROM $WEATHER_CONDITION_TABLE_NAME WHERE id = :id")
    suspend fun getById(id: Long): WeatherConditionEntity?
}