package com.ashishvz.moviereview.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ashishvz.moviereview.BuildConfig
import com.ashishvz.moviereview.api.TMBDApi
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.utility.Constants
import com.ashishvz.moviereview.utility.Constants.TMDB_STARTING_PAGE_INDEX

class MoviePagingSource(
    private val tmbdApi: TMBDApi
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = tmbdApi.getPopularMovies(
                BuildConfig.API_KEY,
                pageIndex
            )
            val movies = if (response.isSuccessful) response.body()!!.results else throw java.lang.Exception("Api failed")
            val nextKey = if (movies.isEmpty()) null else pageIndex + (params.loadSize / Constants.PAGE_SIZE)
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}