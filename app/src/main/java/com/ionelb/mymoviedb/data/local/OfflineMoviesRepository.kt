package com.ionelb.mymoviedb.data.local

import com.ionelb.mymoviedb.data.model.OfflineMovie
import javax.inject.Inject

/**
 * Repository module that handles data operation related to the OfflineMovieDatabase.
 */
class OfflineMoviesRepository @Inject constructor(
    private val offlineMovieDao: OfflineMoviesDao
){
    suspend fun insertMovie(movie: OfflineMovie) = offlineMovieDao.insertMovie(movie)
    fun getOfflineMovies() = offlineMovieDao.getOfflineMovies()
    suspend fun deleteAllMovies() = offlineMovieDao.deleteAllMovies()
}