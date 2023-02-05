package ru.kurgin.tinkoff

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.kurgin.tinkoff.databinding.ItemFilmsBinding
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film
import java.io.ByteArrayOutputStream

interface IFilmActionListener {
    fun onFilmDetails(film: Film)
    fun addFilmToFavorite(film: Film, bitmap: Bitmap)
}

open class FilmsAdapter(
    private val filmActionListener: IFilmActionListener,
    private val fragment: Fragment
) :
    RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>(), View.OnClickListener,
    View.OnLongClickListener {
    var films: MutableList<Film> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmsBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
        return FilmsViewHolder(binding)
    }

    override fun getItemCount(): Int = films.size


    override fun onClick(v: View) {
        filmActionListener.onFilmDetails(v.tag as Film)
    }

    override fun onLongClick(v: View): Boolean {
        if (v.findViewById<ImageView>(R.id.film_poster)?.drawable != null) {
            filmActionListener.addFilmToFavorite(
                v.tag as Film,
                v.findViewById<ImageView>(R.id.film_poster).drawable.toBitmap()
            )
        }
        return true
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        val currentFilm = films[position]
        holder.itemView.tag = currentFilm
        with(holder.binding) {
            filmTitle.text = currentFilm.nameRu ?: currentFilm.nameEn ?: ""
            yearOfRelease.text = currentFilm.year ?: ""
            progressBar.visibility = View.VISIBLE
            Glide.with(fragment)
                .load(currentFilm.posterUrlPreview)
                .dontAnimate()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(
                            fragment.context,
                            Constants.ERROR_WHILE_LOAD_POSTER,
                            Toast.LENGTH_SHORT
                        ).show()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                })
                .into(filmPoster)
        }

    }

    class FilmsViewHolder(
        val binding: ItemFilmsBinding
    ) : RecyclerView.ViewHolder(binding.root)

}