package com.ashishvz.moviereview.data.remote

import androidx.paging.PagingData
import com.ashishvz.moviereview.api.TMBDApi
import com.ashishvz.moviereview.data.remote.model.Movie
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getPopularMovies(): Flow<PagingData<Movie>>
}