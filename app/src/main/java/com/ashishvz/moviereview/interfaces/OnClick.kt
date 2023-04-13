package com.ashishvz.moviereview.interfaces

import com.ashishvz.moviereview.data.remote.model.Movie

interface OnClick {
    fun onMovieClick(movie: Movie)
}