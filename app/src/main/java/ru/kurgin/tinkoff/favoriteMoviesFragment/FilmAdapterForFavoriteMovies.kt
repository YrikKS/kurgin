package ru.kurgin.tinkoff.favoriteMoviesFragment

import android.graphics.BitmapFactory
import android.view.View
import androidx.fragment.app.Fragment
import ru.kurgin.tinkoff.FilmsAdapter
import ru.kurgin.tinkoff.IFilmActionListener

class FilmAdapterForFavoriteMovies(
    private val filmActionListener: IFilmActionListener,
    private val fragment: Fragment
) : FilmsAdapter(filmActionListener, fragment) {

    override fun onLongClick(v: View): Boolean {
        return false
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val currentFilm = films[position]
        holder.itemView.tag = currentFilm
        with(holder.binding) {
            filmTitle.text = currentFilm.nameRu ?: currentFilm.nameEn ?: ""
            yearOfRelease.text = currentFilm.year ?: ""
            filmPoster.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    currentFilm.poster,
                    0,
                    currentFilm.poster!!.size
                )
            )
        }
    }
}