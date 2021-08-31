package com.ionelb.mymoviedb.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity marks a class as an entity. This class will have a mapping SQLite table in the database.
// A basic class representing an entity that is a row in a multi-column database table.
@Entity(tableName = "offline_movies")
data class OfflineMovie(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "key") val key: Int = 0,
    @ColumnInfo(name = "movieId") val id: Int,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "imageUrl") val backdrop_path: String,
    @ColumnInfo(name = "movieTitle") val title: String,
    @ColumnInfo(name = "movieReleaseDate") val release_date: String,
    @ColumnInfo(name = "movieRating") val vote_average: String
)