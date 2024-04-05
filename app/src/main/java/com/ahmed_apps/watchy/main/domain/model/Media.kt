package com.ahmed_apps.watchy.main.domain.model

/**
 * @author Ahmed Guedmioui
 */
data class Media(
    val mediaId: Int,

    val isLiked: Boolean,
    val isBookmarked: Boolean,

    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<String>,
    val mediaType: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    var category: String,

    val runTime: Int,
    val tagLine: String,

    val videosIds: List<String>,
    val similarMediaIds: List<Int>

)


























