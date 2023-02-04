package ru.kurgin.tinkoff.kinopoiskApi.classFromJson

data class PopularFilms(
    val films: List<Film?>,
    val pagesCount: Int?
)