package com.example.animelist.domain

import android.util.Log
import com.example.animelist.di.database.Anime
import com.example.animelist.di.database.AnimeDao
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor(val animeDao: AnimeDao) {
    operator fun invoke(anime: Anime) {
        try {
            animeDao.insertAll(anime)
        }
        catch (exception: Exception){
            Log.e("ADD_TO_FAVORITE", exception.message!!)
        }
    }
}