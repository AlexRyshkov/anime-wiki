package com.example.animelist.data.network

import com.example.animelist.di.BASE_URL
import com.example.animelist.di.database.AnimeListResponse
import com.example.animelist.di.database.AnimeResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiService {
    @GET("anime")
    suspend fun getAnimeList(@Query("page") page: Int, @Query("q") query: String): AnimeListResponse

    @GET("anime/{id}/full")
    suspend fun getAnime(@Path("id") malId: Int): AnimeResponse
}

fun moshiBuilder(): Moshi? {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

fun retrofit(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshiBuilder()))
        .baseUrl(BASE_URL)
        .build()
}

fun retrofitService(): AnimeApiService {
    return retrofit().create(AnimeApiService::class.java)
}