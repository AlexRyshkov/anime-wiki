package com.example.animelist.di.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animelist.data.AnimeDTO

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
    val titleJapanese: String?,
    val type: String?,
    val score: Double?,
    )
