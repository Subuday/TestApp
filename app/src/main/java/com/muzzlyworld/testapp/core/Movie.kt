package com.muzzlyworld.testapp.core

typealias Movies = List<Movie>

data class Movie(
    val id: Int,
    val name: String,
    val description: String,
    val poster: String
)