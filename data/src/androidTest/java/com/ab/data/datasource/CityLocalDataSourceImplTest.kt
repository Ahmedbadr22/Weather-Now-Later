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
import org.junit.Assert.assertNull
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
}
