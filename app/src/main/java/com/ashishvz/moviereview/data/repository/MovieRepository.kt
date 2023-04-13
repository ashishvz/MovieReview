package com.ashishvz.moviereview.data.repository

import androidx.paging.PagingData
import com.ashishvz.moviereview.api.TMBDApi
import com.ashishvz.moviereview.data.remote.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
}