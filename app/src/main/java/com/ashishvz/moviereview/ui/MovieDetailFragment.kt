package com.ashishvz.moviereview.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ashishvz.moviereview.R
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.databinding.FragmentMovieDetailBinding
import com.ashishvz.moviereview.utility.Constants
import com.bumptech.glide.Glide

class MovieDetailFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var movie: Movie
    private val args: MovieDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_movie_detail,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movie = args.data
        binding.apply {
            Glide.with(requireContext())
                .load(if (movie.backdrop_path.isNullOrEmpty()) R.drawable.poster_placeholder else "${Constants.IMG_BASE_URL}${movie.backdrop_path}")
                .into(backdropImage)
            movieName.text = movie.name
            originalMovieName.text = movie.original_name
            movieOverview.text = movie.overview
            ratingText.text = buildString { append(movie.vote_average);append("/10") }
            voteText.text = movie.vote_count.toString()
            languageChip.text = movie.original_language
            countryChip.text = movie.origin_country.single()
        }
    }
}