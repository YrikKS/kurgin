package ru.kurgin.tinkoff.homeFragment

import android.util.Log
import kotlinx.coroutines.sync.Mutex
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.homeFragment.homeInterfase.IHomeModel
import ru.kurgin.tinkoff.homeFragment.homeInterfase.RequestResult
import ru.kurgin.tinkoff.kinopoiskApi.ApiHelper
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film


class HomeModel(val viewModel: HomeViewModel) : IHomeModel {
    var listFilms = mutableListOf<Film>()
        private set
    private var currentLoadedPageFromApi = 1
    private val mutex = Mutex()

    override suspend fun getNewData(): RequestResult {
        try {

            Log.i(
                Constants.TAG_FOR_LOG,
                "get from ip ${Constants.GET_TOP_100 + currentLoadedPageFromApi}"
            )
            mutex.lock()
            listFilms += ApiHelper.kinopoiskApi.getHundredMovies(Constants.GET_TOP_100 + currentLoadedPageFromApi)
                .films.filterNotNull().toMutableList()
            currentLoadedPageFromApi++
            mutex.unlock()
        } catch (ex: Exception) {
            Log.i(Constants.TAG_FOR_LOG, "api request end with exception")
            mutex.unlock()
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