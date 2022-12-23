package com.example.animelist.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnimeDao {
    @Query("Select * from Anime")
    fun getAll() : List<Anime>

    @Insert
    fun insertAll(vararg animeList: Anime)

    @Delete
    fun delete(anime: Anime)
}