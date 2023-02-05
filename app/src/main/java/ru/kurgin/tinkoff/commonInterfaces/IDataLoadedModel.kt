package ru.kurgin.tinkoff.homeFragment.homeInterfase

enum class RequestResult {
    OK,
    DATA_RAN_OUT,
    FATAL_ERROR
}

interface IDataLoadedModel {

    suspend fun getNewData(): RequestResult
}