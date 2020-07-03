package com.muzzlyworld.testapp.core

import com.muzzlyworld.testapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface MovieRepository {
    suspend fun getTrendingMovies(page: Int): Result<Pair<Movies, Int>>
    suspend fun searchMovie(name: String, page: Int) : Result<Pair<Movies, Int>>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>
}

class MovieRepositoryImpl(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getTrendingMovies(page: Int):  Result<Pair<Movies, Int>> = withContext(Dispatchers.IO) {
        executeQuery(
            query = { movieApi.getTrendingMovies(page) },
            mapper = {
                val movies = it.results.map { movieResponse -> movieResponse.toMovie() }
                movies to it.maxPage
            }
        )
    }

    override suspend fun searchMovie(name: String, page: Int): Result<Pair<Movies, Int>> = withContext(Dispatchers.IO) {
        executeQuery(
            query = { movieApi.searchMovie(name, page) },
            mapper = {
                val movies = it.results.map { movieResponse -> movieResponse.toMovie() }
                movies to it.maxPage
            }
        )
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> = withContext(Dispatchers.IO) {
        executeQuery(
            query = { movieApi.getMovieDetails(movieId) },
            mapper = { it.toMovieDetails() }
        )
    }
}

inline fun<T : Any, R : Any> executeQuery(
     query: () -> T,
     mapper: (T) -> R
) : Result<R> = try {
    val data = query()
    Result.Success(mapper(data))
} catch (e: Exception) { Result.Error }