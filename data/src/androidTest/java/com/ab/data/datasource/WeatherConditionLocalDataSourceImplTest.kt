package com.ab.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.city.CityLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSource
import com.ab.data.source.local.datasource.weather_condition.WeatherConditionLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSource
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSourceImpl
import com.ab.data.source.local.db.WeatherNowLaterDatabase
import com.ab.data.source.local.db.dao.CityDao
import com.ab.data.source.local.db.dao.WeatherConditionDao
import com.ab.data.source.local.db.dao.WeatherForecastDao
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherConditionEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherConditionLocalDataSourceImplTest {

    private lateinit var database: WeatherNowLaterDatabase

    private lateinit var cityDao: CityDao
    private lateinit var weatherForecastDao: WeatherForecastDao

    private lateinit var weatherForecastLocalDataSource: WeatherForecastLocalDataSource
    private lateinit var cityLocalDataSource: CityLocalDataSource

    private lateinit var weatherConditionDao: WeatherConditionDao
    private lateinit var weatherConditionLocalDataSource: WeatherConditionLocalDataSource

    private lateinit var context: Context

    private lateinit var cityDummyEntity: CityEntity
    private lateinit var weatherForecastDummyEntity: WeatherForecastEntity
    private lateinit var weatherConditionDummyEntity: WeatherConditionEntity


    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, WeatherNowLaterDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        weatherForecastDao = database.getWeatherForecastDao()
        cityDao = database.getCityDao()

        weatherForecastLocalDataSource = WeatherForecastLocalDataSourceImpl(weatherForecastDao)
        cityLocalDataSource = CityLocalDataSourceImpl(cityDao)

        weatherConditionDao = database.getWeatherConditionDao()
        weatherConditionLocalDataSource = WeatherConditionLocalDataSourceImpl(weatherConditionDao)


        cityDummyEntity = CityEntity(
            id = 1,
            name = "Cairo",
            latitude = 30.0444,
            longitude = 31.2357,
            country = "Egypt",
            timezone = 7200,
            population = 7734614
        )

        weatherForecastDummyEntity = WeatherForecastEntity(
            id = 1,
            cityId = 0,
            dateTimestamp = 1741946400,
            sunrise = 1741925173,
            sunset = 1741968140,
            pressure = 1016,
            humidity = 16,
            windSpeed = 7.89,
            windDirectionInDegree = 41
        )

        weatherConditionDummyEntity = WeatherConditionEntity(
            id = 1,
            forecastId = 0,
            weatherId = 800,
            main = "Clear",
            description = "clear sky",
            icon = "01d"
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertWeatherCondition_returnId() = runTest {
        val insertedCityId = cityLocalDataSource.insert(cityDummyEntity)

        val insertedWeatherForecastId = weatherForecastLocalDataSource.insert(weatherForecastDummyEntity.copy(cityId = insertedCityId))

        val insertedWeatherConditionId = weatherConditionLocalDataSource.insert(weatherConditionDummyEntity.copy(forecastId = insertedWeatherForecastId))

        assertEquals(1, insertedWeatherConditionId)
    }
}
