package ru.kurgin.tinkoff

object Constants {
    const val TAG_FOR_LOG = "kinopoisk tag"
    const val ERROR_WHILE_LOAD_POSTER = "error while load poster"


    //retrofit constants
    const val API_URL = "https://kinopoiskapiunofficial.tech"
    const val GET_FILM_DETAILS = "/api/v2.2/films/"
    const val GET_TOP_100 = "/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page="
    const val ACCEPT_HEADER = "accept: application/json"
    const val API_KEY_HEADER = "X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
}