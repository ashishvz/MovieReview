package com.ashishvz.moviereview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ashishvz.moviereview.R
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.data.ui.MovieListAdapter
import com.ashishvz.moviereview.databinding.FragmentMoviesBinding
import com.ashishvz.moviereview.interfaces.OnClick
import com.ashishvz.moviereview.utility.Utility
import com.ashishvz.moviereview.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(), OnClick {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MovieViewModel by viewModel()
    private lateinit var adapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            adapter = MovieListAdapter(requireContext(), this@MovieFragment)
            recycler.layoutManager = GridLayoutManager(requireContext(), 2)
            recycler.adapter = adapter
        }
        if (Utility.isInternetAvailable(requireContext()))
            collectUiState()
        else {
            binding.contentLoadingBar.hide()
            binding.noInternet.visibility = View.VISIBLE
        }
    }

    private fun collectUiState() {
        lifecycleScope.launch(Dispatchers.IO) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPopularMovies()
                    .collectLatest { movies ->
                        withContext(Dispatchers.Main) {
                            binding.contentLoadingBar.hide()
                        }
                        adapter.submitData(movies)
                    }
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        findNavController().navigate(
            MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
                movie
            )
        )
    }
}