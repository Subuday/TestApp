package com.muzzlyworld.testapp.core

data class MovieDetails(
    val id: Int,
    val name: String,
    val description: String,
    val poster: String,
    val releaseDate: String,
    val genres: Genres,
    val cast: Cast,
    val directors: Directors
)

typealias Genres = List<Genre>
data class Genre(val name: String)

typealias Cast = List<Actor>
data class Actor(val character: String, val name: String, val image: String)

typealias Directors = List<Director>
data class Director(val name: String, val image: String)