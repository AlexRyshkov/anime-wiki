package com.example.animelist.network

import com.squareup.moshi.Json

data class Response(
    val data: List<Anime>
)

data class Anime(
    val title: String,
    val images: Images
)

data class Images(
    val webp: Webp
)

data class Webp(
    val image_url: String
)
