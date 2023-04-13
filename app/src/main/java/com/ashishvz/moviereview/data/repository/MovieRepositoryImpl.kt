package com.ashishvz.moviereview.data.repository

import androidx.paging.PagingData
import com.ashishvz.moviereview.data.remote.RemoteDataSource
import com.ashishvz.moviereview.data.remote.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): MovieRepository {
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
       return remoteDataSource.getPopularMovies()
    }
}