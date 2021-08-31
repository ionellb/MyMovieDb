package com.ionelb.mymoviedb.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Movies model containing the movie details
 */
@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val backdrop_path: String,
    val title: String,
    val release_date: String,
    val vote_average: String
): Parcelable