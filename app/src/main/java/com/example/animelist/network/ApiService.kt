package com.example.animelist.network

import com.example.animelist.data.model.AnimeListResponse
import com.example.animelist.data.model.AnimeResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.jikan.moe/v4/"

interface ApiService {
    @GET("anime")
    suspend fun getAnimeList(@Query("page") page: Int, @Query("q") query: String): AnimeListResponse

    @GET("anime/{id}/full")
    suspend fun getAnime(@Path("id") malId: Int): AnimeResponse
}

@InstallIn(SingletonComponent::class)
@Module
object AnimeApi {

    @Provides
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRetrofit(factory: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
}