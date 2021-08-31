package com.ionelb.mymoviedb.viewmodel

import androidx.lifecycle.ViewModel
import com.ionelb.mymoviedb.data.local.OfflineMoviesRepository
import com.ionelb.mymoviedb.data.model.OfflineMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the [MainActivity] that provides data from the Room database
 */
@HiltViewModel
class OfflineMoviesViewModel @Inject constructor(
    private val repository: OfflineMoviesRepository
): ViewModel() {

    fun insertMovie(movie: OfflineMovie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertMovie(movie)
        }
    }
    fun deleteAllMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAllMovies()
        }
    }

    fun getOfflineMovies() = repository.getOfflineMovies()
}