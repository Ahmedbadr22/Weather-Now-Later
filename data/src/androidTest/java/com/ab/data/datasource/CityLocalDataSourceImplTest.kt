package com.ab.data.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ab.data.source.local.datasource.city.CityLocalDataSourceImpl
import com.ab.data.source.local.db.WeatherNowLaterDatabase
import com.ab.data.source.local.db.dao.CityDao
import com.ab.domain.model.entity.CityEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityLocalDataSourceImplTest {

    private lateinit var database: WeatherNowLaterDatabase
    private lateinit var cityDao: CityDao
    private lateinit var cityLocalDataSource: CityLocalDataSourceImpl

    private lateinit var context: Context


    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, WeatherNowLaterDatabase::class.java)
            .allowMainThreadQueries() // Required for testing
            .build()

        cityDao = database.getCityDao()
        cityLocalDataSource = CityLocalDataSourceImpl(cityDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCity_returnId() = runTest {
        val city = CityEntity(
            id = 1,
            name = "Cairo",
            latitude = 30.0444,
            longitude = 31.2357,
            country = "Egypt",
            timezone = 7200,
            population = 7734614
        )

        val result = cityLocalDataSource.insert(city)

        assertEquals(1, result)
    }
}
