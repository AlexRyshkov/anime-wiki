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

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    override val getAllFavoriteUseCase: GetAllFavoriteUseCase,
    override val addFavoriteUseCase: AddFavoriteUseCase,
    override val removeFavoriteUseCase: RemoveFavoriteUseCase,
) : BaseAnimeViewModel() {
    init {
        getAllFavorite()
    }
}