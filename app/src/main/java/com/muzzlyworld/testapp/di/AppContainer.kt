package com.muzzlyworld.testapp.di

import com.muzzlyworld.testapp.core.MovieApi
import com.muzzlyworld.testapp.core.MovieRepository
import com.muzzlyworld.testapp.core.MovieRepositoryImpl
import com.muzzlyworld.testapp.ui.movies.SearchedMoviePaginator
import com.muzzlyworld.testapp.ui.movies.TrendingMoviePaginator
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppContainer {

    private val movieApi = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MovieApi::class.java)

    val movieRepository: MovieRepository = MovieRepositoryImpl(movieApi)

    val trendingMoviePaginator get() = TrendingMoviePaginator(movieRepository)
    val searchedMoviePaginator get() = SearchedMoviePaginator(movieRepository)
}