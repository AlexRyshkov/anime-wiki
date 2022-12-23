package com.example.animelist.network

import com.example.animelist.database.AnimeListResponse
import com.example.animelist.database.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("anime")
    suspend fun getAnimeList(@Query("page") page: Int, @Query("q") query: String): AnimeListResponse

    @GET("anime/{id}/full")
    suspend fun getAnime(@Path("id") malId: Int): AnimeResponse
}