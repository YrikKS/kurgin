package ru.kurgin.tinkoff.dataBase

import android.provider.BaseColumns

object FilmsContract : BaseColumns {
    const val TABLE_NAME = "films"
    const val COLUMN_ID_API = "filmId"
    const val COLUMN_NAME_RU = "nameRu"
    const val COLUMN_NAME_EN = "nameEn"
    const val COLUMN_NAME_YEAR = "year"
    const val COLUMN_NAME_POSTER = "poster"


    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Films.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_ID_API INTEGER NOT NULL UNIQUE," +
            "${COLUMN_NAME_RU} TEXT," +
            "${COLUMN_NAME_EN} TEXT," +
            "${COLUMN_NAME_YEAR} TEXT," +
            "${COLUMN_NAME_POSTER} BLOB, " +
            "UNIQUE ($COLUMN_ID_API) ON CONFLICT IGNORE)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FilmsContract.TABLE_NAME}"
}