package com.muzzlyworld.testapp.ui.movie

import com.muzzlyworld.testapp.core.Cast
import com.muzzlyworld.testapp.core.Directors

data class MovieViewState(
    val poster: String,
    val name: String,
    val description: String,
    val releaseDate: String,
    val directors: Directors,
    val cast: Cast,
    val genres: String,
    val isLoading: Boolean,
    val isError: Boolean
) {

    companion object {
        @JvmStatic
        fun idle() = MovieViewState(
            poster = "",
            name = "",
            description = "",
            releaseDate = "",
            directors = listOf(),
            cast = listOf(),
            genres = "",
            isLoading = false,
            isError = false
        )
    }
}