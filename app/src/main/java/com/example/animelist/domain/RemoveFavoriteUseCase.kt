package com.example.animelist.domain

import com.example.animelist.di.database.Anime
import com.example.animelist.di.database.AnimeDao
import javax.inject.Inject

class RemoveFavoriteUseCase @Inject constructor(private val animeDao: AnimeDao) {
    operator fun invoke(anime:Anime) {
        return animeDao.delete(anime)
    }
}