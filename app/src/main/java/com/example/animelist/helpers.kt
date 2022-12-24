package com.example.animelist

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.animelist.data.AnimeDTO
import com.example.animelist.database.Anime

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun AnimeDTO.toEntity(): Anime {
    let {
        return Anime(it.malId, it.title, it.images.webp.large_image_url, it.episodes)
    }
}