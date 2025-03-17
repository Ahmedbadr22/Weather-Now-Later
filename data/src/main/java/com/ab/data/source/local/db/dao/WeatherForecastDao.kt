package com.ab.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ab.core.utils.constants.DB.WEATHER_FORECAST_TABLE_NAME
import com.ab.domain.model.entity.WeatherForecastEntity

@Dao
interface WeatherForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeatherForecastEntity): Long

    @Query("SELECT * FROM $WEATHER_FORECAST_TABLE_NAME")
    suspend fun getAll(): List<WeatherForecastEntity>

    @Query("SELECT * FROM $WEATHER_FORECAST_TABLE_NAME WHERE id = :id")
    suspend fun getById(id: Long): WeatherForecastEntity?
}