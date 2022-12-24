package com.example.animelist.di

import android.content.Context
import androidx.room.Room
import com.example.animelist.data.AppDatabase
import com.example.animelist.database.AnimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppDatabaseModule {

    @Provides
    fun provideAnimeDao(database: AppDatabase) : AnimeDao {
        return database.animeDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "anime.db"
        ).allowMainThreadQueries().build()
    }
}