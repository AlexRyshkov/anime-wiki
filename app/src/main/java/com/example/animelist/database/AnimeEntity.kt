package com.example.animelist.database

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class AnimeListResponse(
    val data: MutableList<Anime>
)

data class AnimeResponse(
    val data: Anime
)

@Entity
data class Anime(
    val title: String,
    @Json(name = "mal_id")
    @PrimaryKey
    val malId: Int,
    @Ignore val images: Images,
    val episodes: Int?,
)

data class Images(
    val webp: Webp
)

data class Webp(
    val large_image_url: String
)
