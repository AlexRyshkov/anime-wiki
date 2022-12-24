package com.example.animelist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.database.Anime
import com.example.animelist.database.AnimeDao
import com.example.animelist.network.ApiService
import com.example.animelist.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

// Лучше вынести в пакет с моделями
enum class AnimeApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class AnimeViewModel @Inject constructor(
    val animeApi: ApiService,
    val animeDao: AnimeDao,
) : ViewModel() {
    // Можно обойтись одной live data, создав вместо enum иерархию классов, которые будут уже у себя
    //  инкапсулировать статус, данные, позицию и проч.
    private val _animeList = MutableLiveData<MutableList<Anime>>(mutableListOf())
    private val _favoriteList = MutableLiveData<MutableList<Anime>>(mutableListOf())
    private val _status = MutableLiveData<AnimeApiStatus>()

    var currentPage: Int = 1

    private var _anime = MutableLiveData<Anime?>()
    val anime: LiveData<Anime?> = _anime

    val status: LiveData<AnimeApiStatus> = _status
    val animeList: LiveData<MutableList<Anime>> = _animeList
    val favoriteList: LiveData<MutableList<Anime>> = _favoriteList

    private var _query: String = ""


    init {
        getAnimeList()
    }

    fun clearAnime() {
        _anime.value = null
    }

    fun updateQuery(query: String) {
        currentPage = 1
        _query = query
        _animeList.value?.clear()
        getAnimeList()
    }

    fun nextPage() {
        currentPage++
        getAnimeList()
    }

    fun getAnime(malId: Int) {
        viewModelScope.launch {
            _status.value = AnimeApiStatus.LOADING

            try {
                val response = animeApi.getAnime(malId)
                _anime.value = response.data.toEntity()
                _status.value = AnimeApiStatus.DONE
            } catch (exception: Exception) {
                _status.value = AnimeApiStatus.ERROR
            }
        }
    }

    fun loadFavorite() {
        viewModelScope.launch {
            val favoriteList =  animeDao.getAll().toMutableList()
            _favoriteList.value = favoriteList
        }
    }

    fun addToFavorite(anime: Anime) {
        try {
            animeDao.insertAll(anime)
            _favoriteList.value?.add(anime)
        }
        catch (exception: Exception){
            Log.e("ADD_TO_FAVORITE", exception.message!!)
        }
    }

    fun removeFromFavorite(anime: Anime) {
        try {
            animeDao.delete(anime)
            _favoriteList.value?.remove(anime)
        }
        catch (exception: Exception){
            Log.e("REMOVE_FROM_FAVORITE", exception.message!!)
        }
    }

    fun isInFavorite(malId: Int) :Boolean {
        if (favoriteList.value != null) {
            return favoriteList.value!!.any { it.malId == malId }
        }
        return false;
    }

    private fun getAnimeList() {
        // IO операции запускать на Dispatchers.IO
        viewModelScope.launch {
            _status.value = AnimeApiStatus.LOADING
            try {
                val response = animeApi.getAnimeList(currentPage, _query)
                addToList(response.data.map { it.toEntity() })
                _status.value = AnimeApiStatus.DONE
            } catch (e: Exception) {
                _status.value = AnimeApiStatus.ERROR
                addToList(listOf())
            }
        }
    }

    private fun addToList(items: Iterable<Anime>) {
        val currentList = _animeList.value
        currentList?.addAll(items)
        _animeList.value = currentList!!
    }
}