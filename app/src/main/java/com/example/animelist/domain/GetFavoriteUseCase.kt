package com.example.animelist.domain

import androidx.lifecycle.viewModelScope
import com.example.animelist.data.database.Anime
import com.example.animelist.data.database.AnimeDao
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(private val animeDao: AnimeDao) {

    suspend fun invoke() : MutableList<Anime> {
            return animeDao.getAll().toMutableList()
    }
}