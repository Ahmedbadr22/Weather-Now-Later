package com.ab.data.source.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ab.core.utils.constants.DB.DATABASE_NAME
import com.ab.data.source.local.db.dao.CityDao
import com.ab.data.source.local.db.dao.TemperatureDao
import com.ab.data.source.local.db.dao.WeatherConditionDao
import com.ab.data.source.local.db.dao.WeatherForecastDao
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
    version = 1,
    exportSchema = true
)
abstract class WeatherNowLaterDatabase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
    abstract fun getTemperatureDao(): TemperatureDao
    abstract fun getWeatherForecastDao(): WeatherForecastDao
    abstract fun getWeatherConditionDao(): WeatherConditionDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherNowLaterDatabase? = null

        fun getInstance(context: Context): WeatherNowLaterDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WeatherNowLaterDatabase::class.java,
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