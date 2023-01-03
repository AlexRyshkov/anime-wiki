package com.example.animelist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.di.database.Anime
import com.example.animelist.di.database.AnimeDao
import com.example.animelist.domain.FetchAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AnimeApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class HomeViewModel @Inject constructor(
    val animeDao: AnimeDao,
    val fetchAnimeUseCase: FetchAnimeUseCase,
) : ViewModel() {
    private val _animeList = MutableLiveData<MutableList<Anime>>(mutableListOf())
    private val _status = MutableLiveData<AnimeApiStatus>()
    private val _anime = MutableLiveData<Anime?>()
    private val _query =  MutableLiveData("")


    private var _currentPage: Int = 1

    val anime: LiveData<Anime?> = _anime
    val status: LiveData<AnimeApiStatus> = _status
    val animeList: LiveData<MutableList<Anime>> = _animeList
    val query: LiveData<String> = _query


    init {
        getAnimeList()
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun searchWithQuery() {
        _currentPage = 1
        _animeList.value?.clear()
        getAnimeList()
    }

    fun nextPage() {
        _currentPage++
        getAnimeList()
    }

    private fun getAnimeList() {
        viewModelScope.launch {
            _status.value = AnimeApiStatus.LOADING
            try {
                val data = fetchAnimeUseCase.invoke(_currentPage, _query.value!!)
                addToList(data)
                _status.value = AnimeApiStatus.DONE
            } catch (e: Exception) {
                Log.e("FETCH_ANIME_USE_CASE", e.message!!)
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