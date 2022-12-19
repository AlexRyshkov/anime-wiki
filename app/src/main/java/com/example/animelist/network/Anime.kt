package com.example.animelist.network

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
    val episodes: Int?,
    val images: Images
)

data class Images(
    val webp: Webp
)

data class Webp(
    val image_url: String
)
