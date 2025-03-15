package com.ab.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ab.core.utils.constants.DB.CITY_TABLE_NAME
import com.ab.domain.model.entity.relations.CityWithWeatherForecastDetailsEntityRel
import com.ab.domain.model.entity.CityEntity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CityEntity): Long

    @Query("SELECT * FROM $CITY_TABLE_NAME")
    suspend fun getAll(): List<CityEntity>

    @Transaction
    @Query("SELECT * FROM $CITY_TABLE_NAME")
    suspend fun getAllWithRelations(): List<CityWithWeatherForecastDetailsEntityRel>

    @Transaction
    @Query("SELECT * FROM $CITY_TABLE_NAME WHERE name LIKE '%' || :name || '%'")
    suspend fun getCityWeatherForecastByName(name: String): CityWithWeatherForecastDetailsEntityRel?

    @Query("SELECT * FROM $CITY_TABLE_NAME WHERE id = :id")
    suspend fun getById(id: Long): CityEntity?


    @Query("DELETE FROM $CITY_TABLE_NAME WHERE id = :id")
    suspend fun deleteById(id: Long)
}