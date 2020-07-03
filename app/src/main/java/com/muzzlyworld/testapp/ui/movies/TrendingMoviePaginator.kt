package com.muzzlyworld.testapp.ui.movies

import com.muzzlyworld.testapp.core.Movie
import com.muzzlyworld.testapp.core.MovieRepository
import com.muzzlyworld.testapp.utils.Paginator
import com.muzzlyworld.testapp.utils.Result

class TrendingMoviePaginator(
    private val movieRepository: MovieRepository
) : Paginator<Movie>() {
    override suspend fun loadLatest(startPage: Int): Result<Pair<List<Movie>, Int>> = movieRepository.getTrendingMovies(startPage)
    override suspend fun loadNext(page: Int): Result<Pair<List<Movie>, Int>> = movieRepository.getTrendingMovies(page)
}