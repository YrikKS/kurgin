package ru.kurgin.tinkoff.kinopoiskApi

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.PopularFilms

interface KinopoiskApi {
    @GET
    @Headers(Constants.ACCEPT_HEADER, Constants.API_KEY_HEADER)
    suspend fun getHundredMovies(@Url url : String): PopularFilms
}