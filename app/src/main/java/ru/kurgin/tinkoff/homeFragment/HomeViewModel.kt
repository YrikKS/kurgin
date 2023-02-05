package ru.kurgin.tinkoff.homeFragment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.homeFragment.homeInterfase.RequestResult
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val homeModel = HomeModel(this)

    val listFilms = MutableLiveData<List<Film>>().apply {
        mutableListOf<Film>()
    }
    private var countItemLoadInLastTime = 0

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
                when (homeModel.getNewData()) {
                    RequestResult.OK -> {
                        Log.i(Constants.TAG_FOR_LOG, "request receive access")
                        listFilms.value = homeModel.listFilms.apply {
                            countItemLoadInLastTime = size
                        }
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