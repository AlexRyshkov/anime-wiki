package com.example.animelist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.animelist.database.Anime
import com.example.animelist.database.AnimeDao

@Database(entities=[Anime::class], version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}