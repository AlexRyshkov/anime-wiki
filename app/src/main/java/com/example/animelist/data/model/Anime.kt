package com.example.animelist.data.model

import com.squareup.moshi.Json

data class AnimeListResponse(
    val data: MutableList<Anime>
)

data class AnimeResponse(
    val data: Anime
)

data class Anime(
    val title: String,
    @Json(name = "mal_id")
    val malId: Int,
    val images: Images,
    val episodes: Int?,
    )

data class Images(
    val webp: Webp
)

data class Webp(
    val large_image_url: String
)
