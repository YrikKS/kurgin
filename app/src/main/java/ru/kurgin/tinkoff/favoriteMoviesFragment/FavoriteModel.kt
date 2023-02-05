package ru.kurgin.tinkoff.favoriteMoviesFragment

import ru.kurgin.tinkoff.dataBase.DbManager
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

enum class DbRequestResult {
    SUCCESS,
    ERROR
}

class FavoriteModel(
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel,
    private val dbManager: DbManager
) {

    var listFilms = mutableListOf<Film>()
        private set

    suspend fun loadNewData(): DbRequestResult {
        listFilms = dbManager.getFilmFromDb()
        return DbRequestResult.SUCCESS
    }
}