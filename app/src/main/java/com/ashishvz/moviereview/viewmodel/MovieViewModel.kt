package com.ashishvz.moviereview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return movieRepository.getPopularMovies()
    }
}