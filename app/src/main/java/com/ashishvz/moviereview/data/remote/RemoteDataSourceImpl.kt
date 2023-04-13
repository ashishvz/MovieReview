package com.ashishvz.moviereview.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ashishvz.moviereview.data.paging.MoviePagingSource
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.utility.Constants
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val moviePagingSource: MoviePagingSource
) : RemoteDataSource {
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = Constants.PAGE_SIZE
            ),
            pagingSourceFactory = {
                moviePagingSource
            }
        ).flow
    }
}