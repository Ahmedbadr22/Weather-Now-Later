package com.ab.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.city.CityLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSource
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSourceImpl
import com.ab.data.source.local.db.WeatherNowLaterDatabase
import com.ab.data.source.local.db.dao.CityDao
import com.ab.data.source.local.db.dao.WeatherForecastDao
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherForecastLocalDataSourceImplTest {

    private lateinit var database: WeatherNowLaterDatabase

    private lateinit var cityDao: CityDao
    private lateinit var weatherForecastDao: WeatherForecastDao

    private lateinit var weatherForecastLocalDataSource: WeatherForecastLocalDataSource
    private lateinit var cityLocalDataSource: CityLocalDataSource

    private lateinit var context: Context


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
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertWeatherForecast_returnId() = runTest {
        val city = CityEntity(
            id = 1,
            name = "Cairo",
            latitude = 30.0444,
            longitude = 31.2357,
            country = "Egypt",
            timezone = 7200,
            population = 7734614
        )

        val createdCityId = cityLocalDataSource.insert(city)

        val weatherForecastEntity = WeatherForecastEntity(
            id = 1,
            cityId = createdCityId,
            dateTimestamp = 1741946400,
            sunrise = 1741925173,
            sunset = 1741968140,
            pressure = 1016,
            humidity = 16,
            windSpeed = 7.89,
            windDirectionInDegree = 41
        )

        val result = weatherForecastLocalDataSource.insert(weatherForecastEntity)

        assertEquals(1, result)
    }
}
