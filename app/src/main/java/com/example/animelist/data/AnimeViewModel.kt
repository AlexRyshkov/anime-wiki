package com.example.animelist.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.network.Anime
import com.example.animelist.network.AnimeApi
import kotlinx.coroutines.launch

class AnimeViewModel : ViewModel() {
    val animeList : MutableLiveData<List<Anime>> = MutableLiveData()
    var currentPage: Int = 1

    init {
        viewModelScope.launch {
            animeList.value = AnimeApi.retrofitService.getAnimeList(currentPage).data
        }
    }

    fun nextPage () {
        currentPage++
        viewModelScope.launch {
            animeList.value = AnimeApi.retrofitService.getAnimeList(currentPage).data
        }
    }
}