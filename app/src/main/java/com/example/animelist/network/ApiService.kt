package com.example.animelist.network

import com.example.animelist.data.model.AnimeListResponse
import com.example.animelist.data.model.AnimeResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.jikan.moe/v4/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ApiService {
    @GET("anime")
    suspend fun getAnimeList(@Query("page") page: Int, @Query("q") query: String): AnimeListResponse

    @GET("anime/{id}/full")
    suspend fun getAnime(@Path("id") malId: Int): AnimeResponse
}

// Добавить DI фреймоворк
object AnimeApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}