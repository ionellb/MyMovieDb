package com.ionelb.mymoviedb.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ionelb.mymoviedb.paging.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton repository for fetching data from remote source
 */
@Singleton
class MoviesRepository @Inject constructor(private val tmdbApi: TMDbApi) {

    fun getTopRatedMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {MoviesPagingSource(tmdbApi, null)}
        ).liveData

    fun getSearchedMovies(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {MoviesPagingSource(tmdbApi, query)}
        ).liveData

}