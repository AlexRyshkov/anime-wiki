package com.example.animelist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.data.database.Anime
import com.example.animelist.data.database.AnimeDao
import com.example.animelist.domain.GetFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseAnimeViewModel :
    ViewModel() {
    private val _favoriteList = MutableLiveData<List<Anime>>()
    abstract val getFavoriteUseCase: GetFavoriteUseCase

    val favoriteList
        get() = _favoriteList

    suspend fun getFavoriteFromDatabase() {
        val result = getFavoriteUseCase.invoke()
        _favoriteList.value = result
    }

    fun isInFavorite(malId: Int) :Boolean {
        val favoriteList = favoriteList.value
        if (favoriteList != null) {
            return favoriteList.any { it.malId == malId }
        }
        return false;
    }
}