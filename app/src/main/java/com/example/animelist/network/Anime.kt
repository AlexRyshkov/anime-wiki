package com.example.animelist.network

data class Response(
    val data: MutableList<Anime>
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
