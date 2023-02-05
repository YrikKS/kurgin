package ru.kurgin.tinkoff.filmDetailsFragment

import android.util.Log
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.homeFragment.homeInterfase.IDataLoadedModel
import ru.kurgin.tinkoff.homeFragment.homeInterfase.RequestResult
import ru.kurgin.tinkoff.kinopoiskApi.ApiHelper
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.FilmDetails

class FilmDetailsModel(
    private val viewModel: FilmDetailsViewModel,
    private val currentIdLoad: Int
) : IDataLoadedModel {
    lateinit var filmDetails: FilmDetails

    override suspend fun getNewData(): RequestResult {
        try {
            Log.i(
                Constants.TAG_FOR_LOG,
                "get from ip ${Constants.GET_FILM_DETAILS + currentIdLoad}"
            )
            filmDetails =
                ApiHelper.kinopoiskApi.getFilmDetails(Constants.GET_FILM_DETAILS + currentIdLoad)
        } catch (ex: Exception) {
            Log.i(Constants.TAG_FOR_LOG, "api request end with exception")
            ex.printStackTrace()
            return when (ex) {
                is retrofit2.HttpException -> {
                    if (ex.code() == 400) {
                        RequestResult.DATA_RAN_OUT
                    } else {
                        RequestResult.FATAL_ERROR
                    }
                }
                else -> {
                    RequestResult.FATAL_ERROR
                }
            }
        }
        return RequestResult.OK
    }
}