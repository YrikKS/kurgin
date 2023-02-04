package ru.kurgin.tinkoff.kinopoiskApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiHelper {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val kinopoiskApi by lazy {
        retrofit.create(KinopoiskApi::class.java)
    }
}