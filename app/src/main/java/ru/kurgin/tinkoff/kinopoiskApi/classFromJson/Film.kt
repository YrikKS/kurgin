package ru.kurgin.tinkoff.kinopoiskApi.classFromJson

data class Film(
    val countries: List<Country?>? = null,
    val filmId: Int,
    val filmLength: String? = null,
    val genres: List<Genre?>? = null,
    val nameEn: String? = null,
    val nameRu: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val rating: String? = null,
    val ratingChange: String? = null,
    val ratingVoteCount: Int? = null,
    val year: String? = null,
    val poster: ByteArray? = null
)