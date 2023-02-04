package ru.kurgin.tinkoff.homeFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class HomeViewModel : ViewModel() {
    val homeModel = HomeModel(this)
    private val _listFilms = MutableLiveData<List<Film>>().apply {
        mutableListOf<Film>()
    }
    val listFilms: MutableLiveData<List<Film>> = _listFilms


}