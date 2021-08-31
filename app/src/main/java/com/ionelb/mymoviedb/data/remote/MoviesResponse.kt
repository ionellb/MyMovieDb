package com.ionelb.mymoviedb.data.remote

import com.ionelb.mymoviedb.data.model.Movie


data class MoviesResponse (
    val results: List<Movie>
        )