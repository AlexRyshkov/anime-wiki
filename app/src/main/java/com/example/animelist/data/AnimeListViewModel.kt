package com.example.animelist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.network.Anime
import com.example.animelist.network.AnimeApi
import kotlinx.coroutines.launch

enum class AnimeApiStatus { LOADING, ERROR, DONE }


class AnimeViewModel : ViewModel() {
    private val _animeList = MutableLiveData<MutableList<Anime>>(mutableListOf())
    private val _status = MutableLiveData<AnimeApiStatus>()
    var currentPage: Int = 1


    val status: LiveData<AnimeApiStatus> = _status
    val animeList: LiveData<MutableList<Anime>> = _animeList


    init {
        getAnimeList()
    }

    private fun getAnimeList() {
        viewModelScope.launch {
            _status.value = AnimeApiStatus.LOADING
            try {
                val response = AnimeApi.retrofitService.getAnimeList(currentPage)
                addToList(response.data)
                _status.value = AnimeApiStatus.DONE
            } catch (e: Exception) {
                _status.value = AnimeApiStatus.ERROR
                addToList(listOf())
            }
        }
    }

    private fun addToList(items: Iterable<Anime>) {
        val currentList = (animeList.value as MutableList<Anime>)
        currentList.addAll(items)
        _animeList.value = currentList
    }

    fun nextPage() {
        currentPage++
        getAnimeList()
    }
}