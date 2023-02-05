package ru.kurgin.tinkoff.dataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class DbManager(context: Context) {
    private val dbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

    fun openDb() {
        try {
            db = dbHelper.writableDatabase
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun closeDp() {
        dbHelper.close()
    }

    fun insertFilmToDb(film: Film, img: ByteArray) {
        val values = ContentValues().apply {
            put(FilmsContract.COLUMN_NAME_RU, film.nameRu)
            put(FilmsContract.COLUMN_NAME_EN, film.nameEn)
            put(FilmsContract.COLUMN_NAME_YEAR, film.year)
            put(FilmsContract.COLUMN_ID_API, film.filmId)
            put(FilmsContract.COLUMN_NAME_POSTER, img)
        }
        db?.insert(FilmsContract.TABLE_NAME, null, values)
    }

    fun insertFilmToDb(film: Film) {
        val values = ContentValues().apply {
            put(FilmsContract.COLUMN_NAME_RU, film.nameRu)
            put(FilmsContract.COLUMN_NAME_EN, film.nameEn)
            put(FilmsContract.COLUMN_NAME_YEAR, film.year)
            put(FilmsContract.COLUMN_ID_API, film.filmId)
        }
    }


    fun getFilmFromDb(): MutableList<Film> {
        val list = mutableListOf<Film>()
        val cursor = db?.query(FilmsContract.TABLE_NAME, null, null, null, null, null, null)
        cursor.apply {
            while (this?.moveToNext()!!) {
                if (cursor?.getColumnIndex(FilmsContract.COLUMN_NAME_EN)!! >= 0) {
                    list.add(
                        Film(
                            filmId = cursor.getInt(cursor.getColumnIndexOrThrow(FilmsContract.COLUMN_ID_API)),
                            nameEn = cursor.getString(cursor.getColumnIndexOrThrow(FilmsContract.COLUMN_NAME_EN)),
                            nameRu = cursor.getString(cursor.getColumnIndexOrThrow(FilmsContract.COLUMN_NAME_RU)),
                            year = cursor.getString(cursor.getColumnIndexOrThrow(FilmsContract.COLUMN_NAME_YEAR)),
                            poster = cursor.getBlob(cursor.getColumnIndexOrThrow(FilmsContract.COLUMN_NAME_POSTER))
                        )
                    )
                }
            }
        }
        return list
    }

    fun isFilmInDb(idElement: Int): Boolean {
        val cursor = db?.query(
            FilmsContract.TABLE_NAME,
            arrayOf(FilmsContract.COLUMN_ID_API),
            "${FilmsContract.COLUMN_ID_API} = ?",
            arrayOf(idElement.toString()),
            null,
            null,
            null
        )
        return cursor != null && cursor.count > 0
    }
}