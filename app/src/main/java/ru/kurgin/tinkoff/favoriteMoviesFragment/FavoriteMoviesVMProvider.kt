package ru.kurgin.tinkoff.favoriteMoviesFragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kurgin.tinkoff.dataBase.DbManager

class FavoriteMoviesVMProvider(
    private val dbManager: DbManager,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteMoviesViewModel(application, dbManager) as T
    }

}