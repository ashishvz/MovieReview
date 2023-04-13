package com.ashishvz.moviereview.api


import com.ashishvz.moviereview.data.remote.model.PopularMovieResponse
import com.ashishvz.moviereview.utility.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMBDApi {
    @GET(Constants.BASE_URL)
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("page") page: Int): Response<PopularMovieResponse>
}