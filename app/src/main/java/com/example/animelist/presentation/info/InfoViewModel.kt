package com.example.animelist.presentation.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animelist.data.database.Anime
import com.example.animelist.domain.FetchAnimeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

enum class ApiFetchStatus { LOADING, ERROR, DONE }

class InfoViewModel @Inject constructor(
    private val fetchAnimeUseCase: FetchAnimeUseCase
) : ViewModel() {
    private var _anime = MutableLiveData<Anime?>()
    private var _status = MutableLiveData<ApiFetchStatus>()

    val anime: Anime?
        get() = _anime.value

    suspend fun fetchAnime(malId: Int) = withContext(Dispatchers.IO) {
        try {
            _status.value = ApiFetchStatus.LOADING
            _anime.value = fetchAnimeUseCase.invoke(malId)
            _status.value = ApiFetchStatus.DONE
        } catch (exception: Exception) {
            _status.value = ApiFetchStatus.ERROR
        }
    }
}