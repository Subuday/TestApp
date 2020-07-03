package com.muzzlyworld.testapp.core

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    val id: Int,
    @Json(name = "title") val name: String,
    @Json(name = "overview") val description: String?,
    @Json(name = "poster_path") val poster: String?,
    @Json(name = "release_date") val releaseDate: String,
    @Json(name = "genres") val genres: List<GenreResponse>,
    @Json(name = "credits") val credits: CreditsResponse
) {

    fun toMovieDetails(): MovieDetails = MovieDetails(
        id,
        name,
        description ?: "",
        if (poster != null) "https://image.tmdb.org/t/p/w500/$poster" else "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1811/pavelstasevich181101065/112815953-stock-vector-no-image-available-icon-flat-vector.jpg?ver=6",
        releaseDate,
        genres.map { it.toGenre() },
        credits.cast.map { it.toActor() },
        credits.crew.filter { it.job == "Director" }.map {
            Director(
                it.name,
                if (it.image != null) "https://image.tmdb.org/t/p/w500/${it.image}" else "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1811/pavelstasevich181101065/112815953-stock-vector-no-image-available-icon-flat-vector.jpg?ver=6"
            )
        }
    )

}

@JsonClass(generateAdapter = true)
data class GenreResponse(val name: String) {
    fun toGenre() = Genre(name)
}

@JsonClass(generateAdapter = true)
data class CreditsResponse(val cast: List<CastResponse>, val crew: List<CrewResponse>)

@JsonClass(generateAdapter = true)
data class CastResponse(
    val character: String,
    val name: String,
    @Json(name = "profile_path") val image: String?
) {
    fun toActor() = Actor(
        character,
        name,
        if (image != null) "https://image.tmdb.org/t/p/w500/$image" else "https://us.123rf.com/450wm/pavelstasevich/pavelstasevich1811/pavelstasevich181101065/112815953-stock-vector-no-image-available-icon-flat-vector.jpg?ver=6"
    )
}

@JsonClass(generateAdapter = true)
data class CrewResponse(
    val job: String,
    val name: String,
    @Json(name = "profile_path") val image: String?
)

