package com.ashishvz.moviereview.data.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.ashishvz.moviereview.BuildConfig
import com.ashishvz.moviereview.api.TMBDApi
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.data.remote.model.PopularMovieResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.spy
import org.mockito.Mockito.`when`
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MoviePagingSourceTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var tmdbApi: TMBDApi

    private val mockMovieData = listOf(
        Movie(
            backdrop_path = "/A6uiMhZaJA6KHmzx1qTJfNMaWuf.jpg",
            first_air_date = "2010-08-11",
            genre_ids = listOf(35),
            id = 5295,
            name = "Al-Kabir Awy",
            origin_country = listOf("EG"),
            original_name = "الكبير أوي",
            original_language = "ar",
            overview = "Al-Kabeer Away is an Egyptian comedy television series starring Ahmed Makki, Donia Samir Ghanem, Hisham Ismail and Mohamed Shaheen",
            popularity = 2272.048,
            poster_path = "/oj4XM6wpGRIcx3QoQx1PF1fx5E5.jpg",
            vote_average = 7.0,
            vote_count = 31
        )
    )

    @Before
    fun setUp() {
        tmdbApi = spy(TMBDApi::class.java)
    }

    @Test
    fun loadReturnsPageWhenOnSuccessfulLoadOfPageKeyedData() = runTest {
        val pagingSource = MoviePagingSource(tmdbApi)
        `when`(tmdbApi.getPopularMovies(BuildConfig.API_KEY, 1)).thenReturn(
            Response.success(
                PopularMovieResponse(
                    page = 1,
                    results = mockMovieData,
                    total_pages = 2,
                    total_results = 10
                )
            )
        )
        assertEquals(
            PagingSource.LoadResult.Page(
                data = mockMovieData,
                prevKey = null,
                nextKey = 1
            ),
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
}