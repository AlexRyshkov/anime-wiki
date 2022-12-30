package com.example.animelist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animelist.di.database.Anime
import com.example.animelist.di.database.AnimeDao

@Database(entities=[Anime::class], version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}