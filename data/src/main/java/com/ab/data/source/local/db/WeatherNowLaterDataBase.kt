package com.ab.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ab.core.utils.constants.DB.DATABASE_NAME
import com.ab.data.source.local.dao.CityDao
import com.ab.data.source.local.dao.TemperatureDao
import com.ab.data.source.local.dao.WeatherConditionDao
import com.ab.data.source.local.dao.WeatherForecastDao
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherConditionEntity

@Database(
    entities = [
        CityEntity::class,
        WeatherForecastEntity::class,
        TemperatureEntity::class,
        WeatherConditionEntity::class
    ],
    exportSchema = false,
    version = 1
)
abstract class WeatherNowLaterDataBase : RoomDatabase() {
    abstract val cityDao: CityDao
    abstract val temperatureDao: TemperatureDao
    abstract val weatherForecastDao: WeatherForecastDao
    abstract val weatherConditionDao: WeatherConditionDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherNowLaterDataBase? = null

        fun getInstance(context: Context): WeatherNowLaterDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WeatherNowLaterDataBase::class.java,
                    DATABASE_NAME
                )
                    .build()
                    .also { database ->
                        INSTANCE = database
                    }
            }
        }

    }
}