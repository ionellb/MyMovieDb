package com.ionelb.mymoviedb.di

import android.content.Context
import androidx.room.Room
import com.ionelb.mymoviedb.data.local.OfflineMoviesDatabase
import com.ionelb.mymoviedb.data.remote.TMDbApi
import com.ionelb.mymoviedb.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


// Hilt module used for cases where you cannot constructor-inject a type due to not owning
// the classes or because they come from external libraries (Retrofit, Room database)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesTMDbApi(retrofit: Retrofit) : TMDbApi =
        retrofit.create(TMDbApi::class.java)

    @Singleton
    @Provides
    fun providesOfflineMovieDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context,
            OfflineMoviesDatabase::class.java,
            "offline_movies"
        ).build()

    @Singleton
    @Provides
    fun providesOfflineMovieDao(database: OfflineMoviesDatabase) =
        database.movieDao()

}