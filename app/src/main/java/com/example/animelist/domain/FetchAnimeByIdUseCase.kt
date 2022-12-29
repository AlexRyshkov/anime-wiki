package com.example.animelist.domain

import com.example.animelist.data.database.Anime
import com.example.animelist.data.network.AnimeApiService
import com.example.animelist.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FetchAnimeByIdUseCase @Inject constructor(
    private val animeApi: AnimeApiService
) {

    suspend fun invoke(malId: Int): Anime  {
        val response = withContext(Dispatchers.IO) {
             animeApi.getAnime(malId)
        }
        return response.data.toEntity()
    }
}