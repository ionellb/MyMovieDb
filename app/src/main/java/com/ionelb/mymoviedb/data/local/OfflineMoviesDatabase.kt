package com.ionelb.mymoviedb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ionelb.mymoviedb.data.model.OfflineMovie

/**
 * This is the database for storing the top 10 movies from the api.
 * It is instantiated as a singleton using Hilt (see AppModule.kt).
 */
@Database(entities = [OfflineMovie::class], version = 1, exportSchema = false)
abstract class OfflineMoviesDatabase: RoomDatabase() {

    /**
     * Returns the OfflineMovieDao.
     */
    abstract  fun movieDao(): OfflineMoviesDao

}
