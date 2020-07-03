package com.muzzlyworld.testapp.ui.movies

import androidx.lifecycle.*
import com.muzzlyworld.testapp.utils.Event
import com.muzzlyworld.testapp.utils.Result.Error
import com.muzzlyworld.testapp.utils.Result.Success
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val trendingMoviePaginator: TrendingMoviePaginator,
    private val searchedMoviePaginator: SearchedMoviePaginator
) : ViewModel() {

    private val _state = MutableLiveData(MoviesViewState.idle())
    val state: LiveData<MoviesViewState> get() = _state
    
    private val _effect = MutableLiveData<Event<Effect>>()
    val effect: LiveData<Event<Effect>> get() = _effect

    init {
        loadTrendingMovies()
    }

    private fun loadTrendingMovies() = viewModelScope.launch {
        _state.value = _state.value!!.copy(showLoading = true)
        when (val result = trendingMoviePaginator.loadLatestData()) {
            is Success -> _state.value = _state.value!!.copy(trendingMovies = result.data, showLoading = false, showError = false)
            is Error -> _state.value = _state.value!!.copy(trendingMovies = listOf(), showLoading = false, showError = true)
        }
    }

    fun loadNextMovies(lastVisibleItemPosition: Int) {
        if(_state.value!!.isSearching) loadNextSearchedMovies(lastVisibleItemPosition)
        else loadNextTrendingMovies(lastVisibleItemPosition)
    }

    private fun loadNextTrendingMovies(lastVisibleItemPosition: Int) {
        if (!checkIfNextTrendingMoviesCanBeLoaded(lastVisibleItemPosition)) return
        viewModelScope.launch {
            _state.value = _state.value!!.copy(showLoading = true)
            when(val result = trendingMoviePaginator.loadNextData()) {
                is Success -> {
                    val state = _state.value!!
                    _state.value = state.copy(trendingMovies = state.trendingMovies + result.data, showLoading = false, showError = false)
                }
                is Error -> {
                    _state.value = _state.value!!.copy(trendingMovies = listOf(), showLoading = false, showError = true)
                    onSearchIconClick()
                }
            }
        }
    }

    private fun loadNextSearchedMovies(lastVisibleItemPosition: Int) {
        if(!checkIfNextSearchedMoviesCanBeLoaded(lastVisibleItemPosition)) return
        viewModelScope.launch {
            _state.value = _state.value!!.copy(showLoading = true)
            when(val result = searchedMoviePaginator.loadNextData()) {
                is Success -> {
                    val state = _state.value!!
                    _state.value = state.copy(searchedMovies = state.searchedMovies + result.data, showLoading = false, showError = false)
                }
                is Error -> {
                    _state.value = _state.value!!.copy(searchedMovies = listOf(), showLoading = false, showError = true)
                    onSearchIconClick()
                }
            }
        }
    }

    private var lastSearchJob: Job? = null
    private var lastSearchName: String = ""
    fun searchMovie(name: String) {
        val searchName = name.trim()
        if(lastSearchName == searchName || searchName.isBlank()) return

        lastSearchJob?.cancel()
        lastSearchName = searchName

        lastSearchJob = viewModelScope.launch {
            delay(300)
            when(val result = searchedMoviePaginator.loadLatestByName(searchName)) {
                is Success -> _state.value = _state.value!!.copy(searchedMovies = result.data, isSearching = true, showLoading = false, showError = false)
                is Error -> _state.value = _state.value!!.copy(searchedMovies = listOf(), showLoading = false, showError = true)
            }
        }
    }

    fun onSearchIconClick() {
        if(_state.value!!.isSearching) {
            _effect.value = Event(Effect.ClearSearchEffect)
            _state.value = _state.value!!.copy(searchedMovies = listOf(), isSearching =  false)
        }
    }


    private fun checkIfNextTrendingMoviesCanBeLoaded(lastVisibleItemPosition: Int): Boolean {
        val state = _state.value!!
        return (lastVisibleItemPosition >= state.trendingMovies.size - 5) && !(state.showLoading || state.showError || trendingMoviePaginator.isAllDataLoaded())
    }

    private fun checkIfNextSearchedMoviesCanBeLoaded(lastVisibleItemPosition: Int) : Boolean {
        val state = _state.value!!
        return (lastVisibleItemPosition >= state.searchedMovies.size - 5) && !(state.showLoading || state.showError || searchedMoviePaginator.isAllDataLoaded())
    }


}