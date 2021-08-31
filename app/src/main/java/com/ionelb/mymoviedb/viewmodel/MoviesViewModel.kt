package com.ionelb.mymoviedb.viewmodel

import androidx.hilt.Assisted
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ionelb.mymoviedb.data.remote.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for the [MainActivity] that provides data from the remote repository
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    @Assisted state: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val EMPTY_QUERY = ""
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, EMPTY_QUERY)

    private var toSave = MutableLiveData<Boolean>()


    fun getToSave() : LiveData<Boolean> {
        return toSave
    }

    fun setToSaved() {
        toSave.postValue(true)
    }

    val movies = currentQuery.switchMap { query ->
        if (query.isNotEmpty()) {
            repository.getSearchedMovies(query).cachedIn(viewModelScope)
        } else {
            repository.getTopRatedMovies().cachedIn(viewModelScope)
        }
    }


    fun searchMovie(query: String) {
        currentQuery.value = query
    }



}