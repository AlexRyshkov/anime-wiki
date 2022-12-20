package com.example.animelist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.data.model.Anime
import com.example.animelist.network.AnimeApi
import kotlinx.coroutines.launch

// Лучше вынести в пакет с моделями
enum class AnimeApiStatus { LOADING, ERROR, DONE }


class AnimeViewModel : ViewModel() {
    // Можно обойтись одной live data, создав вместо enum иерархию классов, которые будут уже у себя
    //  инкапсулировать статус, данные, позицию и проч.
    private val _animeList = MutableLiveData<MutableList<Anime>>(mutableListOf())
    private val _status = MutableLiveData<AnimeApiStatus>()

    var currentPage: Int = 1

    private var _anime = MutableLiveData<Anime?>()
    val anime: LiveData<Anime?> = _anime

    val status: LiveData<AnimeApiStatus> = _status
    val animeList: LiveData<MutableList<Anime>> = _animeList

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
                val response = AnimeApi.retrofitService.getAnime(malId)
                _anime.value = response.data
                _status.value = AnimeApiStatus.DONE
            }
            catch (exception: Exception) {
                _status.value = AnimeApiStatus.ERROR
            }
        }
    }

    private fun getAnimeList() {
        // IO операции запускать на Dispatchers.IO
        viewModelScope.launch {
            _status.value = AnimeApiStatus.LOADING
            try {
                val response = AnimeApi.retrofitService.getAnimeList(currentPage, _query)
                addToList(response.data)
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