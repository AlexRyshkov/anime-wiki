package com.example.animelist

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.animelist.data.AnimeDTO
import com.example.animelist.di.database.Anime

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun AnimeDTO.toEntity(): Anime {
    let {
        return Anime(malId, title, images.webp.large_image_url, episodes, titleJapanese, type, score, url)
    }
}

fun getLayoutManagerSpanCount(widthWindowSizeClass: WindowSizeClass): Int {
    return when (widthWindowSizeClass) {
        WindowSizeClass.COMPACT -> 2
        WindowSizeClass.MEDIUM -> 3
        WindowSizeClass.EXPANDED -> 4
    }
}