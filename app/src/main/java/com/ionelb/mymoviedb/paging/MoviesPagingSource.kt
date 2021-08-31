package com.ionelb.mymoviedb.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ionelb.mymoviedb.data.model.Movie
import com.ionelb.mymoviedb.data.remote.TMDbApi
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_NUMBER = 1

/**
 * Page-keyed PagingSource, which uses Int page number to load pages.
 * Loads Items from network requests via Retrofit to TMDb api.
 */
class MoviesPagingSource(
    private val tmdbApi: TMDbApi,
    private val query: String?
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        /**
         * Retrofit calls that return the body type throw either IOException for network
         * failures, or HttpException for any non-2xx HTTP status codes.
         */
        return try {
            /** Key may be null during a refresh, if no explicit key is passed into Pager
            * construction. Use [START_PAGE_NUMBER] as default, because our API is indexed started at index 1
            */
            val position = params.key ?: START_PAGE_NUMBER

            /**
             * Suspending network load via Retrofit. This doesn't need to be wrapped in a
             * withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine
             * CallAdapter dispatches on a worker thread.
             */
            val response =
                if (query != null) tmdbApi.searchMovie(query, position)
                else tmdbApi.getTopRatedMovies(position)

            val movies = response.results


            LoadResult.Page(
                data = movies,
                prevKey = if (position == START_PAGE_NUMBER) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        TODO("Not yet implemented")
    }
}