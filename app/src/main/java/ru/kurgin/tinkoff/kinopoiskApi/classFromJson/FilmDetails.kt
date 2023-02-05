package ru.kurgin.tinkoff.kinopoiskApi.classFromJson

data class FilmDetails(
    val completed: Boolean?,
    val countries: List<Country>?,
    val coverUrl: Any?,
    val description: String?,
    val editorAnnotation: Any?,
    val endYear: Any?,
    val filmLength: Int?,
    val genres: List<Genre>?,
    val has3D: Boolean?,
    val hasImax: Boolean?,
    val imdbId: Any?,
    val isTicketsAvailable: Boolean?,
    val kinopoiskId: Int?,
    val lastSync: String?,
    val logoUrl: Any?,
    val nameEn: Any?,
    val nameOriginal: Any?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val productionStatus: Any?,
    val ratingAgeLimits: String?,
    val ratingAwait: Int?,
    val ratingAwaitCount: Int?,
    val ratingFilmCritics: Any?,
    val ratingFilmCriticsVoteCount: Int?,
    val ratingGoodReview: Any?,
    val ratingGoodReviewVoteCount: Int?,
    val ratingImdb: Any?,
    val ratingImdbVoteCount: Int?,
    val ratingKinopoisk: Int?,
    val ratingKinopoiskVoteCount: Int?,
    val ratingMpaa: Any?,
    val ratingRfCritics: Any?,
    val ratingRfCriticsVoteCount: Int?,
    val reviewsCount: Int?,
    val serial: Boolean?,
    val shortDescription: Any?,
    val shortFilm: Boolean?,
    val slogan: Any?,
    val startYear: Any?,
    val type: String?,
    val webUrl: String?,
    val year: Int?
)