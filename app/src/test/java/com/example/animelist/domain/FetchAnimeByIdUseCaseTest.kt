package com.example.animelist.domain

import com.example.animelist.data.network.AnimeApiService
import com.example.animelist.data.network.retrofitService
import com.example.animelist.di.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class FetchAnimeByIdUseCaseTest {
    @Test
    fun testFetchAnimeById() = runBlocking {
        val id = 1
        val apiService = retrofitService()
        val response = apiService.getAnime(id)
        assert(response.data.malId == id)
    }
}