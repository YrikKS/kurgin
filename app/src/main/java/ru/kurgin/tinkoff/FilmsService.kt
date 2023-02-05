package ru.kurgin.tinkoff

import androidx.lifecycle.MutableLiveData
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class FilmsService {
    val listFilms = MutableLiveData<List<Film>>().apply {
        mutableListOf<Film>()
    }

    fun setFilms(newData: List<Film>) {
        listFilms.value = newData
    }
}