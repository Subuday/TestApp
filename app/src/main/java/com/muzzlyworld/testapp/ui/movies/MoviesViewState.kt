package com.muzzlyworld.testapp.ui.movies

import com.muzzlyworld.testapp.core.Movies

data class MoviesViewState(
    val searchedMovies: Movies,
    val trendingMovies: Movies,
    val isSearching: Boolean,
    val showLoading: Boolean,
    val showError: Boolean
) {

    companion object {
        @JvmStatic
        fun idle() = MoviesViewState(listOf(), listOf(), isSearching = false, showLoading = false, showError = false)
    }
}

sealed class Effect {
    object ClearSearchEffect : Effect()
}