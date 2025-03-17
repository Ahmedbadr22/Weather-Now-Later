package com.ab.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ab.core.utils.constants.DB.CITY_TABLE_NAME
import java.time.LocalDateTime

@Entity(tableName = CITY_TABLE_NAME)
data class CityEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val longitude: Double,
    val latitude: Double,
    val country: String,
    val population: Long,
    val timezone: Int,
    val lastSearchDate: LocalDateTime = LocalDateTime.now()
)
