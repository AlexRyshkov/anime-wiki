package com.example.animelist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.di.database.Anime
import com.example.animelist.domain.AddFavoriteUseCase
import com.example.animelist.domain.GetAllFavoriteUseCase
import com.example.animelist.domain.RemoveFavoriteUseCase
import kotlinx.coroutines.launch

abstract class BaseAnimeViewModel  :
    ViewModel() {
    private val _favoriteList = MutableLiveData<List<Anime>>()
    abstract val getAllFavoriteUseCase: GetAllFavoriteUseCase
    abstract val addFavoriteUseCase: AddFavoriteUseCase
    abstract val removeFavoriteUseCase: RemoveFavoriteUseCase

    fun getAllFavorite() {
        viewModelScope.launch {
            val result = getAllFavoriteUseCase()
            result.collect {
                _favoriteList.value = it
            }
        }
    }

    val favoriteList
        get() = _favoriteList

    fun addToFavorite(anime: Anime) {
        addFavoriteUseCase(anime)
    }

    fun removeFavorite(anime: Anime) {
        removeFavoriteUseCase(anime)
    }

    fun isInFavorite(malId: Int) :Boolean {
        val favoriteList = favoriteList.value
        if (favoriteList != null) {
            return favoriteList.any { it.malId == malId }
        }
        return false;
    }
}