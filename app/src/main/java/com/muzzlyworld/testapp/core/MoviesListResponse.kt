package com.muzzlyworld.testapp.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesListResponse(
    @Json(name = "total_pages") val maxPage: Int,
    val results: List<TrendingMoviesResponse>
)

@JsonClass(generateAdapter = true)
data class TrendingMoviesResponse(
    val id: Int,
    @Json(name = "title")
    val name: String,
    @Json(name = "poster_path")
    val poster: String?,
    @Json(name = "overview")
    val description: String
) {

    fun toMovie() = Movie(
        id,
        name,
        description,
        if (poster != null) "https://image.tmdb.org/t/p/w500/$poster" else "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1811/pavelstasevich181101065/112815953-stock-vector-no-image-available-icon-flat-vector.jpg?ver=6"
    )
}