package com.example.animelist.domain

import com.example.animelist.data.network.AnimeApiService
import com.example.animelist.di.database.Anime
import com.example.animelist.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FetchAnimeUseCase @Inject constructor(
    private val animeApi: AnimeApiService
) {
    suspend fun invoke(page: Int, query: String = ""): List<Anime> {
        val response = withContext(Dispatchers.IO) {
            animeApi.getAnimeList(page, query)
        }
        return response.data.map {
            it.toEntity()
        }
    }
}