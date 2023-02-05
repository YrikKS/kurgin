package ru.kurgin.tinkoff.homeFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import ru.kurgin.tinkoff.*
import ru.kurgin.tinkoff.databinding.FragmentHomeBinding
import ru.kurgin.tinkoff.kinopoiskApi.classFromJson.Film

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FilmsAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.button.setOnClickListener {
//            findNavController().navigate(R.id.action_navigation_home_to_filmDetailsFragment)
//        }
        setupRecyclerView()
        setupSearch()
        return binding.root
    }

    private fun setupSearch() {
        binding.searchTextFieldHome.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                println("beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                println("onTextChanged")
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    homeViewModel.listFilms.value?.filter {
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
                    homeViewModel.listFilms.value?.forEach {
                        adapter.films.add(it)
//                        adapter.notifyItemRangeInserted(adapter.films.size - 1, 1)
                    }
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }

    private fun setupRecyclerView() {
        adapter = FilmsAdapter(object : IFilmActionListener {
            override fun onFilmDetails(film: Film) {
                Log.i(Constants.TAG_FOR_LOG, "click on ${film.filmId}")
                val action =
                    HomeFragmentDirections.actionNavigationHomeToFilmDetailsFragment(filmId = film.filmId)
                findNavController().navigate(action)
            }

            override fun addFilmToFavorite(film: Film) {
                Log.i(Constants.TAG_FOR_LOG, "long click on ${film.nameRu}")
                (activity as MainActivity).dbManager.insertToDb(film)
            }
        }, this)
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewHome.adapter = adapter
        addObserveForRecycleView()

        binding.recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    homeViewModel.setUpdateFilms()
                }
            }
        })
    }

    private fun addObserveForRecycleView() {
        val observer = Observer<List<Film>> { value ->
            value.filterIndexed { index, it ->
                if (adapter.films.size <= index) {
                    return@filterIndexed true
                } else {
                    return@filterIndexed adapter.films[index].filmId != it.filmId
                }
            }.forEach {
                adapter.films.add(it)
                adapter.notifyItemRangeInserted(adapter.films.size - 1, 1)
            }
        }
        homeViewModel.listFilms.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}