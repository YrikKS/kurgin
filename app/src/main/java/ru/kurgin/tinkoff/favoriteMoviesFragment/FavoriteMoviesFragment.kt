package ru.kurgin.tinkoff.favoriteMoviesFragment

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kurgin.tinkoff.Constants
import ru.kurgin.tinkoff.FilmsAdapter
import ru.kurgin.tinkoff.IFilmActionListener
import ru.kurgin.tinkoff.MainActivity
import ru.kurgin.tinkoff.databinding.FragmentFavoriteBinding
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class FavoriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding!!
    private lateinit var adapter: FilmAdapterForFavoriteMovies
    private lateinit var favoriteMoviesViewModel: FavoriteMoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteMoviesViewModel = FavoriteMoviesVMProvider(
            (activity as MainActivity?)?.dbManager!!,
            activity?.application!!
        ).create(FavoriteMoviesViewModel::class.java)
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        createRecycleView()
        setupSearch()
        return binding.root
    }


    private fun setupSearch() {
        binding.searchTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println("beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println("onTextChanged")
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    favoriteMoviesViewModel.listFilms.value?.filter {
                        it.nameRu?.contains(s.subSequence(0, s.length - 1), true) ?: false
                    }.apply {
                        if (this?.size!! > 0) {
                            adapter.films.clear()
                            adapter.notifyDataSetChanged()
                            this.forEach {
                                adapter.films.add(it)
                                adapter.notifyItemRangeInserted(adapter.films.size - 1, 1)
                            }
                        }
                    }
                } else {
                    adapter.films.clear()
                    favoriteMoviesViewModel.listFilms.value?.forEach {
                        adapter.films.add(it)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }

    private fun createRecycleView() {
        adapter = FilmAdapterForFavoriteMovies(object : IFilmActionListener {
            override fun onFilmDetails(film: Film) {
                Log.i(Constants.TAG_FOR_LOG, "click on ${film.filmId}")
                val action =
                    FavoriteMoviesFragmentDirections.actionNavigationFavoritesToFilmDetailsFragment(
                        filmId = film.filmId
                    )
                findNavController().navigate(action)
            }

            override fun addFilmToFavorite(film: Film, bitmap: Bitmap) {
                Log.i(Constants.TAG_FOR_LOG, "long click on ${film.nameRu}")
            }
        }, this)
        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFavorite.adapter = adapter
        addObserveForRecycleView()
    }

    private fun addObserveForRecycleView() {
        val observer = Observer<List<Film>> { value ->
            adapter.films.clear()
            adapter.notifyDataSetChanged()
            value.forEach {
                println(it.nameRu)
                adapter.films.add(it)
                adapter.notifyItemRangeInserted(adapter.films.size - 1, 1)
            }
        }
        favoriteMoviesViewModel.listFilms.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}