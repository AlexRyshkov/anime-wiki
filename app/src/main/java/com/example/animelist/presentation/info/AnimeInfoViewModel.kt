package com.example.animelist.presentation.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.animelist.di.database.Anime
import com.example.animelist.domain.AddFavoriteUseCase
import com.example.animelist.domain.FetchAnimeByIdUseCase
import com.example.animelist.domain.GetAllFavoriteUseCase
import com.example.animelist.domain.RemoveFavoriteUseCase
import com.example.animelist.presentation.BaseAnimeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ApiFetchStatus { LOADING, ERROR, DONE }

@HiltViewModel
class AnimeInfoViewModel @Inject constructor(
    private val fetchAnimeUseCase: FetchAnimeByIdUseCase,
    override val addFavoriteUseCase: AddFavoriteUseCase,
    override val removeFavoriteUseCase: RemoveFavoriteUseCase,
    override val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseAnimeViewModel() {
    private val _anime = MutableLiveData<Anime?>()
    private val _status = MutableLiveData<ApiFetchStatus>()
    private val _malId: Int = requireNotNull(savedStateHandle["malId"])

    init {
        getAllFavorite()
        viewModelScope.launch {
            fetchAnime(_malId)
        }
    }

    val anime
        get() = _anime

    val status
        get() = _status

    suspend fun fetchAnime(malId: Int) {
        try {
            _status.value = ApiFetchStatus.LOADING
            _anime.value = fetchAnimeUseCase.invoke(malId)
            _status.value = ApiFetchStatus.DONE
        } catch (exception: Exception) {
            _status.value = ApiFetchStatus.ERROR
        }
    }
}