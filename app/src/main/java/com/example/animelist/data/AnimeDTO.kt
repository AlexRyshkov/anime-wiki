package com.example.animelist.data

import com.squareup.moshi.Json

data class AnimeDTO(
    @Json(name = "mal_id")
    val malId: Int,
    val title: String,
    val episodes: Int?,
    val images: Images,
)

data class Images(
    val webp: Webp
)

data class Webp(
    val large_image_url: String
)

