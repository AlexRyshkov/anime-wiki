package com.example.animelist.di.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("Select * from Anime")
    fun getAll() : List<Anime>

    @Query("Select * from Anime")
    fun getAllFlow() : Flow<List<Anime>>

    @Insert
    fun insertAll(vararg animeList: Anime)

    @Delete
    fun delete(anime: Anime)
}