package com.example.animelist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.animelist.data.AnimeDTO
import com.squareup.moshi.Json

data class AnimeListResponse(
    val data: MutableList<AnimeDTO>
)

data class AnimeResponse(
    val data: AnimeDTO
)

@Entity
data class Anime(
    @PrimaryKey
    val malId: Int,
    val title: String,
    val image: String,
    val episodes: Int?,
    )
