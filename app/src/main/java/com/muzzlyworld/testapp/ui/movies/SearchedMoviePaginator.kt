package com.muzzlyworld.testapp.ui.movies

import com.muzzlyworld.testapp.core.Movie
import com.muzzlyworld.testapp.core.MovieRepository
import com.muzzlyworld.testapp.utils.Paginator
import com.muzzlyworld.testapp.utils.Result

class SearchedMoviePaginator(
    private val movieRepository: MovieRepository
) : Paginator<Movie>() {

    private var name: String = ""

    suspend fun loadLatestByName(name: String) : Result<List<Movie>> {
        this.name = name
        return loadLatestData()
    }

    override suspend fun loadLatest(startPage: Int): Result<Pair<List<Movie>, Int>> = movieRepository.searchMovie(name, startPage)
    override suspend fun loadNext(page: Int): Result<Pair<List<Movie>, Int>> = movieRepository.searchMovie(name, page)
}