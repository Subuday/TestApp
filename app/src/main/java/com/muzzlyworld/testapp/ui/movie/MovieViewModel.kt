package com.muzzlyworld.testapp.ui.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muzzlyworld.testapp.core.MovieRepository
import com.muzzlyworld.testapp.utils.Result.Error
import com.muzzlyworld.testapp.utils.Result.Success
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieId: Int,
    private val repository: MovieRepository
) : ViewModel() {

    private val _state = MutableLiveData<MovieViewState>(MovieViewState.idle())
    val state: LiveData<MovieViewState> get() = _state

    init {
        loadMovie()
    }

    private fun loadMovie() = viewModelScope.launch {
        _state.value = _state.value!!.copy(isLoading = true)
        when(val result = repository.getMovieDetails(movieId)) {
            is Success -> {
                val movieDetails = result.data
                _state.value = _state.value!!.copy(
                    poster = movieDetails.poster,
                    name = movieDetails.name,
                    description = movieDetails.description,
                    releaseDate = movieDetails.releaseDate,
                    genres = movieDetails.genres.joinToString("*") { it.name },
                    cast = movieDetails.cast,
                    directors = movieDetails.directors,
                    isLoading = false)
            }
            is Error -> _state.value = _state.value!!.copy(isLoading = false, isError = true)
        }
    }

}