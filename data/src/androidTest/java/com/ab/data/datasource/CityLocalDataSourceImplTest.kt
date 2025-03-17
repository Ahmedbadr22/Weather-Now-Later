package com.ab.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ab.data.source.local.datasource.city.CityLocalDataSource
import com.ab.data.source.local.datasource.city.CityLocalDataSourceImpl
import com.ab.data.source.local.db.WeatherNowLaterDatabase
import com.ab.data.source.local.db.dao.CityDao
import com.ab.domain.model.entity.CityEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityLocalDataSourceImplTest {

    private lateinit var database: WeatherNowLaterDatabase
    private lateinit var cityDao: CityDao
    private lateinit var cityLocalDataSource: CityLocalDataSource

    private lateinit var context: Context

    private lateinit var dummyCityEntity: CityEntity


    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, WeatherNowLaterDatabase::class.java)
            .allowMainThreadQueries() // Required for testing
            .build()

        cityDao = database.getCityDao()
        cityLocalDataSource = CityLocalDataSourceImpl(cityDao)

        dummyCityEntity = CityEntity(
            id = 1,
            name = "Cairo",
            latitude = 30.0444,
            longitude = 31.2357,
            country = "Egypt",
            timezone = 7200,
            population = 7734614
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCity_returnInsertedCityId() = runTest {
        val result = cityLocalDataSource.insert(dummyCityEntity)

        assertEquals(1, result)
    }

    @Test
    fun getAll_returnAllInsertedCitiesCorrectly() = runTest {
        cityLocalDataSource.insert(dummyCityEntity) // first city insertion
        cityLocalDataSource.insert(dummyCityEntity.copy(id = 2, name = "Alex")) // second city insertion

        // list all cities
        val cityEntities = cityLocalDataSource.getAll()

        assertEquals(2, cityEntities.size)
    }

    @Test
    fun getById_returnCorrectCity() = runTest {
        val insertedCityId = cityLocalDataSource.insert(dummyCityEntity)
        val insertedCity = cityLocalDataSource.getById(insertedCityId)

        assertEquals(dummyCityEntity.id, insertedCity?.id)
        assertEquals(dummyCityEntity.name, insertedCity?.name)
    }

    @Test
    fun getById_noInsertedCity_returnNull() = runTest {
        val insertedCity = cityLocalDataSource.getById(dummyCityEntity.id)

        assertNull(insertedCity)
    }

    @Test
    fun deleteById_insertOneCity_returnZeroCityInDb() = runTest {
        val insertedCityId = cityLocalDataSource.insert(dummyCityEntity)

        assertEquals(dummyCityEntity.id, insertedCityId)

        val insertedCities = cityLocalDataSource.getAll()

        assertEquals(1, insertedCities.size)

        cityLocalDataSource.deleteById(insertedCityId)

        val insertedCitiesAfterDelete = cityLocalDataSource.getAll()

        assertEquals(0, insertedCitiesAfterDelete.size)
    }

    @Test
    fun getAllWithRelations_insertOneCity_returnTheSizeCorrectly() = runTest {
        cityLocalDataSource.insert(dummyCityEntity)

        val cityEntities = cityLocalDataSource.getAllWithRelations()

        assertEquals(1, cityEntities.size)
    }

    @Test
    fun getAllWithRelations_noInsertedCity_returnEmpty() = runTest {
        val cityEntities = cityLocalDataSource.getAllWithRelations()

        assertTrue(cityEntities.isEmpty())
    }

    @Test
    fun getCityWeatherForecastByName_searchWithValidName_returnCity() = runTest {
        cityLocalDataSource.insert(dummyCityEntity)
        val cityEntity = cityLocalDataSource.getCityWeatherForecastByName(dummyCityEntity.name)

        assertNotNull(cityEntity)
        assertEquals(dummyCityEntity.id, cityEntity?.city?.id)
        assertEquals(dummyCityEntity.name, cityEntity?.city?.name)
    }

    @Test
    fun getCityWeatherForecastByName_searchWithInValidName_returnNull() = runTest {
        cityLocalDataSource.insert(dummyCityEntity)
        val cityEntity = cityLocalDataSource.getCityWeatherForecastByName("Ahm")

        assertNull(cityEntity)
    }

    @Test
    fun getCityWeatherForecastById_searchWithValidId_returnCity() = runTest {
        cityLocalDataSource.insert(dummyCityEntity)
        val cityEntity = cityLocalDataSource.getCityWeatherForecastById(dummyCityEntity.id)

        assertNotNull(cityEntity)
        assertEquals(dummyCityEntity.id, cityEntity?.city?.id)
        assertEquals(dummyCityEntity.name, cityEntity?.city?.name)
    }

    @Test
    fun getCityWeatherForecastById_searchWithInValidId_returnNull() = runTest {
        cityLocalDataSource.insert(dummyCityEntity)
        val cityEntity = cityLocalDataSource.getCityWeatherForecastById(100)

        assertNull(cityEntity)
    }

    @Test
    fun getLastSearchedCity_insertOneCity_returnTheLastInsertedCityCorrectly() = runTest {
        cityLocalDataSource.insert(dummyCityEntity)
        val cityEntity = cityLocalDataSource.getLastSearchedCity()

        assertNotNull(cityEntity)
        assertEquals(dummyCityEntity.id, cityEntity?.city?.id)
        assertEquals(dummyCityEntity.name, cityEntity?.city?.name)
    }

    @Test
    fun getLastSearchedCity_insertTwoCity_returnTheLastInsertedCityCorrectly() = runTest {
        cityLocalDataSource.insert(dummyCityEntity)
        cityLocalDataSource.insert(dummyCityEntity.copy(id = 2, name = "Alex"))

        val cityEntity = cityLocalDataSource.getLastSearchedCity()

        assertNotNull(cityEntity)
        assertEquals(2, cityEntity?.city?.id)
        assertEquals("Alex", cityEntity?.city?.name)
    }
}
