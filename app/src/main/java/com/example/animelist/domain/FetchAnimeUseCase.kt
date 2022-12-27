package com.example.animelist.domain

import com.example.animelist.data.database.Anime
import com.example.animelist.data.network.AnimeApiService
import com.example.animelist.toEntity
import javax.inject.Inject


class FetchAnimeUseCase @Inject constructor(
    private val animeApi: AnimeApiService
) {

    suspend fun invoke(malId: Int): Anime  {
        val response = animeApi.getAnime(malId)
        return response.data.toEntity()
    }
}