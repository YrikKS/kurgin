package ru.kurgin.tinkoff.homeFragment.homeInterfase

enum class RequestResult {
    OK,
    DATA_RAN_OUT,
    FATAL_ERROR
}

interface IHomeModel {

    suspend fun getNewData(): RequestResult
}