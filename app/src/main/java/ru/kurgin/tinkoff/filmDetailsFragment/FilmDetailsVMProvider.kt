package ru.kurgin.tinkoff.filmDetailsFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FilmDetailsVMProvider(private val filmId: Int, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FilmDetailsViewModel(filmId, application) as T
    }

}