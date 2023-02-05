package ru.kurgin.tinkoff.favoriteMoviesFragment

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.FilmsAdapter
import ru.kurgin.tinkoff.IFilmActionListener

class FilmAdapterForFavoriteMovies(
    private val filmActionListener: IFilmActionListener,
    private val fragment: Fragment
) : FilmsAdapter(filmActionListener, fragment) {

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