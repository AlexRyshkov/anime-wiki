package com.example.animelist.database


import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.animelist.data.AppDatabase
import com.example.animelist.di.database.Anime
import com.example.animelist.di.database.AnimeDao
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class DatabaseTest {
    private lateinit var animeDao: AnimeDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        animeDao = db.animeDao()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val animeArray = arrayOf(
            Anime(
                malId = 0,
                title = "title 1",
                image = "image_url",
                episodes = 10,
                titleJapanese = null,
                score = null,
                type = null
            )
        )

        animeDao.insertAll(
            *animeArray
        )
        val animeList = animeDao.getAll()
        for (anime in animeList) {
            assert(animeArray.any { it == anime })
        }
    }
}