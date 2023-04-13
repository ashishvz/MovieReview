package com.ashishvz.moviereview.data.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ashishvz.moviereview.R
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.databinding.CardMovieBinding
import com.ashishvz.moviereview.interfaces.OnClick
import com.ashishvz.moviereview.utility.Constants
import com.bumptech.glide.Glide

class MovieListAdapter(private val context: Context, private val onClick: OnClick) :
    PagingDataAdapter<Movie, MovieListAdapter.MoviePosterViewModel>(MovieDiffCallBack()) {
    private lateinit var binding: CardMovieBinding

    inner class MoviePosterViewModel() : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                Glide.with(context).load(if (movie.poster_path == null) R.drawable.poster_placeholder else "${Constants.IMG_BASE_URL}${movie.poster_path}").error(R.drawable.poster_placeholder).into(moviePosterImageView)
                movieNameText.text = movie.name
                ratingCount.text = movie.vote_average.toString()
                voteCount.text = movie.vote_count.toString()
                movieCard.setOnClickListener {
                    onClick.onMovieClick(movie)
                }
            }
        }
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MoviePosterViewModel, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePosterViewModel {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.card_movie,
            parent,
            false
        )
        return MoviePosterViewModel()
    }
}
