package ru.kurgin.tinkoff.homeFragment

import android.util.Log
//import com.google.gson.GsonBuilder
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.homeFragment.homeInterfase.IHomeModel
import ru.kurgin.tinkoff.kinopoiskApi.ApiHelper
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class HomeModel(val viewModel: HomeViewModel) : IHomeModel {
    private var listFilms = mutableListOf<Film>()
    private var currentLoadedPageFromApi = 1

    override suspend fun getNewData() {
        Log.i(
            Constants.TAG_FOR_LOG,
            "get from ip ${Constants.GET_TOP_100 + currentLoadedPageFromApi}"
        )
        listFilms += ApiHelper.kinopoiskApi.getHundredMovies(Constants.GET_TOP_100 + currentLoadedPageFromApi)
            .films.filterNotNull().toMutableList()
    }
}