package ru.kurgin.tinkoff.favoriteMoviesFragment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.dataBase.DbManager
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class FavoriteMoviesViewModel(application: Application, dbManager: DbManager) :
    AndroidViewModel(application) {
    private val favoriteModel = FavoriteModel(this, dbManager)

    val listFilms = MutableLiveData<List<Film>>().apply {
        mutableListOf<Film>()
    }

    var drawToast: (String) -> Unit = {
        Toast.makeText(application.applicationContext, it, Toast.LENGTH_SHORT).show()
    }

    init {
        setUpdateFilms()
    }

    fun setUpdateFilms() {
        viewModelScope.launch {
            try {
                Log.i(Constants.TAG_FOR_LOG, "vm startLoad")
                when (favoriteModel.loadNewData()) {
                    DbRequestResult.SUCCESS -> {
                        Log.i(Constants.TAG_FOR_LOG, "db access")
                        listFilms.value = favoriteModel.listFilms
                    }
                    DbRequestResult.ERROR -> {
                        Log.i(Constants.TAG_FOR_LOG, "exception load from dp")
                        drawToast("error with dataBase")
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}