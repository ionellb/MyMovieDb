package com.ionelb.mymoviedb.data.remote

import com.ionelb.mymoviedb.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service to fetch information from TheMovieDb Api
 */
interface TMDbApi {

    /**
     * Fetch the top movies from the TMDb using Api Call on given Url and Api Key
     */
    @GET("movie/top_rated?api_key=${Constants.TMDB_API_KEY}")
    suspend fun getTopRatedMovies(
        @Query("page") position: Int
    ): MoviesResponse

    /**
     * Search movies from the TMDb using Api Call on given Url, Api Key and query String
     */
    @GET("search/movie?api_key=${Constants.TMDB_API_KEY}")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesResponse

}