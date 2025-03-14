package com.ab.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.city.CityLocalDataSourceImpl
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSource
import com.ab.data.source.local.datasource.temperature.TemperatureLocalDataSourceImpl
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSource
import com.ab.data.source.local.datasource.weather_forecast.WeatherForecastLocalDataSourceImpl
import com.ab.data.source.local.db.WeatherNowLaterDatabase
import com.ab.data.source.local.db.dao.CityDao
import com.ab.data.source.local.db.dao.TemperatureDao
import com.ab.data.source.local.db.dao.WeatherForecastDao
import com.ab.domain.model.entity.CityEntity
import com.ab.domain.model.entity.TemperatureEntity
import com.ab.domain.model.entity.WeatherForecastEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TemperatureLocalDataSourceImplTest {

    private lateinit var database: WeatherNowLaterDatabase

    private lateinit var cityDao: CityDao
    private lateinit var cityLocalDataSource: CityLocalDataSource

    private lateinit var weatherForecastDao: WeatherForecastDao
    private lateinit var weatherForecastLocalDataSource: WeatherForecastLocalDataSource

    private lateinit var temperatureDao: TemperatureDao
    private lateinit var temperatureLocalDataSource: TemperatureLocalDataSource

    private lateinit var context: Context

    private lateinit var cityEntity: CityEntity
    private lateinit var weatherForecastEntity: WeatherForecastEntity
    private lateinit var temperatureEntity: TemperatureEntity


    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, WeatherNowLaterDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        cityDao = database.getCityDao()
        cityLocalDataSource = CityLocalDataSourceImpl(cityDao)

        weatherForecastDao = database.getWeatherForecastDao()
        weatherForecastLocalDataSource = WeatherForecastLocalDataSourceImpl(weatherForecastDao)

        temperatureDao = database.getTemperatureDao()
        temperatureLocalDataSource = TemperatureLocalDataSourceImpl(temperatureDao)

        cityEntity = CityEntity(
            id = 1,
            name = "Cairo",
            latitude = 30.0444,
            longitude = 31.2357,
            country = "Egypt",
            timezone = 7200,
            population = 7734614
        )

        weatherForecastEntity = WeatherForecastEntity(
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

        temperatureEntity = TemperatureEntity(
            id = 1,
            forecastId = 0,
            dayTemp = 25.5,
            minTemp = 18.0,
            maxTemp = 26.0,
            nightTemp = 16.0,
            eveningTemp = 18.0,
            morningTemp = 20.0,
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertTemperature_returnId() = runTest {

        val insertedCityId = cityLocalDataSource.insert(cityEntity)

        val updatedWeatherForecastEntity = weatherForecastEntity.copy(cityId = insertedCityId)
        val insertedWeatherForecastId = weatherForecastLocalDataSource.insert(updatedWeatherForecastEntity)

        val updatedTemperatureEntity = temperatureEntity.copy(forecastId = insertedWeatherForecastId)

        val insertedTemperatureId = temperatureLocalDataSource.insert(updatedTemperatureEntity)

        assertEquals(1, insertedTemperatureId)

        val insertedTemperatureId2 = temperatureLocalDataSource.insert(updatedTemperatureEntity.copy(id = 2))

        assertEquals(2, insertedTemperatureId2)
    }

    @Test
    fun insertTemperature_WithSameId_ReplaceOnConflictAndReturnId() = runTest {

        // insert required city entity for weather forecast entity
        val insertedCityId = cityLocalDataSource.insert(cityEntity)

        // update weather forecast entity with the inserted city id
        val updatedWeatherForecastEntity = weatherForecastEntity.copy(cityId = insertedCityId)

        // insert weather forecast entity
        val insertedWeatherForecastId = weatherForecastLocalDataSource.insert(updatedWeatherForecastEntity)

        // update temperature entity with the inserted weather forecast id
        val updatedTemperatureEntity = temperatureEntity.copy(forecastId = insertedWeatherForecastId)

        // insert temperature entity
        val insertedTemperatureId = temperatureLocalDataSource.insert(updatedTemperatureEntity)

        // assert that the inserted id should the be the same id of the inserted Entity Object
        assertEquals(1, insertedTemperatureId)

        // insert temperature entity with the same id
        val insertedTemperatureId2 = temperatureLocalDataSource.insert(updatedTemperatureEntity.copy(id = 1))

        // assert that the inserted id should the be the same id of the inserted Entity Object
        assertEquals(1, insertedTemperatureId2)
    }

    @Test
    fun insertTemperature_WithIdZeroInSecondTime_AutoGenerateNewIdAndReturnIt() = runTest {

        // insert required city entity for weather forecast entity
        val insertedCityId = cityLocalDataSource.insert(cityEntity)

        // update weather forecast entity with the inserted city id
        val updatedWeatherForecastEntity = weatherForecastEntity.copy(cityId = insertedCityId)

        // insert weather forecast entity
        val insertedWeatherForecastId = weatherForecastLocalDataSource.insert(updatedWeatherForecastEntity)

        // update temperature entity with the inserted weather forecast id
        val updatedTemperatureEntity = temperatureEntity.copy(forecastId = insertedWeatherForecastId)

        // insert temperature entity
        val insertedTemperatureId = temperatureLocalDataSource.insert(updatedTemperatureEntity)

        // assert that the inserted id should the be the same id of the inserted Entity Object
        assertEquals(1, insertedTemperatureId)

        // insert temperature entity with id 0 to apply auto primary key generation
        val insertedTemperatureId2 = temperatureLocalDataSource.insert(updatedTemperatureEntity.copy(id = 0))

        // assert that the inserted id should the be greater that the first one by one
        assertEquals(2, insertedTemperatureId2)
    }
}
