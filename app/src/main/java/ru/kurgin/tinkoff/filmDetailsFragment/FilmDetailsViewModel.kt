package ru.kurgin.tinkoff.filmDetailsFragment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.homeFragment.homeInterfase.RequestResult
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.FilmDetails

class FilmDetailsViewModel(filmId: Int, application: Application) : AndroidViewModel(application) {
    private val filmDetailModel = FilmDetailsModel(this, filmId)

    val filmDetail = MutableLiveData<FilmDetails>()

    var drawToast: (String) -> Unit = {
        Toast.makeText(application.applicationContext, it, Toast.LENGTH_SHORT).show()
    }

    fun updateFilmDetails() {
        viewModelScope.launch {
            try {
                Log.i(Constants.TAG_FOR_LOG, "vm startLoad")
                when (filmDetailModel.getNewData()) {
                    RequestResult.OK -> {
                        Log.i(Constants.TAG_FOR_LOG, "request receive access")
                        filmDetail.value = filmDetailModel.filmDetails
                    }
                    RequestResult.DATA_RAN_OUT -> {
                        drawToast("data ran out")
                    }
                    RequestResult.FATAL_ERROR -> {
                        drawToast("network error")
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}