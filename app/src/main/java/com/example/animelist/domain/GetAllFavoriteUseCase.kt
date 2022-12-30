package com.example.animelist.domain

import com.example.animelist.di.database.Anime
import com.example.animelist.di.database.AnimeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteUseCase @Inject constructor(private val animeDao: AnimeDao) {

    operator fun invoke() : Flow<List<Anime>> {
        return animeDao.getAllFlow()
    }
}