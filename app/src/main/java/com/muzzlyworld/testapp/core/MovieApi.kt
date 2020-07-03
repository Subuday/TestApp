package com.muzzlyworld.testapp.core

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("/3/trending/movie/week?api_key=80bb1dccaa59bc4ea2fadbb7f93b687c")
    suspend fun getTrendingMovies(@Query("page") page: Int) : MoviesListResponse

    @GET("/3/search/movie?api_key=80bb1dccaa59bc4ea2fadbb7f93b687c")
    suspend fun searchMovie(@Query("query") name: String, @Query("page") page: Int) : MoviesListResponse

    @GET("/3/movie/{id}?api_key=80bb1dccaa59bc4ea2fadbb7f93b687c&append_to_response=credits")
    suspend fun getMovieDetails(@Path("id") id: Int) : MovieDetailsResponse
}