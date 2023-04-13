package com.ashishvz.moviereview.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import com.ashishvz.moviereview.MainCoroutineRule
import com.ashishvz.moviereview.data.remote.RemoteDataSource
import com.ashishvz.moviereview.data.remote.model.Movie
import com.ashishvz.moviereview.utility.toMovieList
import com.google.common.truth.Truth
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.spy

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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

    private val resultList = flow {
        emit(PagingData.from(mockMovieData))
    }

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setup() {
        remoteDataSource = spy(RemoteDataSource::class.java)
        movieRepository = MovieRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `test paging data returns data`() = runTest {
        Mockito.`when`(remoteDataSource.getPopularMovies()).thenReturn(resultList)
        movieRepository.getPopularMovies().test {
            val response = this.awaitItem()
            awaitComplete()
            response.map {
                Truth.assertThat(mockMovieData[0]).isEqualTo(it)
            }
        }
    }


    @Test
    fun `test paging data returns empty`() = runTest {
        val expectedResult = PagingData.from(emptyList<Movie>())
        Mockito.`when`(remoteDataSource.getPopularMovies()).thenReturn(flow { emit(expectedResult) })
        movieRepository.getPopularMovies().test {
            Truth.assertThat(expectedResult.toMovieList()).isEqualTo(awaitItem().toMovieList())
            awaitComplete()
        }
    }
}