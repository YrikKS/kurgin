package ru.kurgin.tinkoff.filmDetailsFragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.helper.widget.Flow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_film_details.*
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.R
import ru.kurgin.tinkoff.databinding.FragmentFilmDetailsBinding
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.FilmDetails

class FilmDetailsFragment : Fragment() {
    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = _binding!!
    private var filmDetailsViewModel: FilmDetailsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println(FilmDetailsFragmentArgs.fromBundle(requireArguments()).filmId)
        filmDetailsViewModel = FilmDetailsVMProvider(
            FilmDetailsFragmentArgs.fromBundle(requireArguments()).filmId,
            activity?.application!!
        ).create(FilmDetailsViewModel::class.java)
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)

        setupView()
        return binding.root
    }

    private fun setupView() {
        applyVisibilityToWidgets(View.GONE)
        binding.progressBar.visibility = View.VISIBLE
        val observer = Observer<FilmDetails> {
            if (it.posterUrl != null) {
                glideExec(it)
            }
            binding.filmName.text = it.nameEn ?: it.nameRu ?: "no name"
            binding.filmDescription.text = it.description
        }
        filmDetailsViewModel?.filmDetail?.observe(viewLifecycleOwner, observer)
        filmDetailsViewModel?.updateFilmDetails()
    }

    private fun glideExec(filmDetails: FilmDetails) {
        Glide.with(this)
            .load(filmDetails.posterUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(
                        this@FilmDetailsFragment.context,
                        Constants.ERROR_WHILE_LOAD_POSTER,
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                    applyVisibilityToWidgets(View.VISIBLE)
                    filmDetails.genres?.forEach { genre ->
                        genre.genre?.let { it1 -> addViewToFlow(binding.flowGenre, it1) }
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    applyVisibilityToWidgets(View.VISIBLE)
                    filmDetails.genres?.forEach { genre ->
                        genre.genre?.let { it1 -> addViewToFlow(binding.flowGenre, it1) }
                    }
                    return false
                }

            })
            .into(binding.posterView)
    }

    private fun applyVisibilityToWidgets(typeVisibility: Int) {
        binding.flowGenre.visibility = typeVisibility
        binding.filmDescription.visibility = typeVisibility
        binding.filmName.visibility = typeVisibility
    }

    private fun addViewToFlow(flow: Flow, string: String) {
        val view = LayoutInflater.from(context).inflate(R.layout.element_flow, null)
        (view as TextView).text = string
        view.layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        view.id = View.generateViewId()
        binding.constraintLayout.addView(view)
        flow.addView(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        filmDetailsViewModel = null
        _binding = null
    }
}