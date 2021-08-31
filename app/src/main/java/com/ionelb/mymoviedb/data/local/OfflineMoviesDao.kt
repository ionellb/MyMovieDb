package com.ionelb.mymoviedb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ionelb.mymoviedb.data.model.OfflineMovie

/**
 * The DAO (data access object) interface that provides methods that the app can query, insert
 * and delete data in the database.
 */

@Dao
interface OfflineMoviesDao {
    @Insert
    suspend fun insertMovie(movie: OfflineMovie)

    /**
     * @Query is used to create custom query.
     * Returns a LiveData object. LiveData observes table live.
     * Whenever data changes to db. it will update data to view.
     */

    @Query("SELECT * FROM offline_movies")
    fun getOfflineMovies() : LiveData<List<OfflineMovie>>

    @Query("DELETE FROM offline_movies")
    suspend fun deleteAllMovies()

}