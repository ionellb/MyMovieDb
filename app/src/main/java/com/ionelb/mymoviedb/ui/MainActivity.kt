package com.ionelb.mymoviedb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ionelb.mymoviedb.R
import com.ionelb.mymoviedb.data.local.OfflineMoviesAdapter
import com.ionelb.mymoviedb.data.model.Movie
import com.ionelb.mymoviedb.data.model.OfflineMovie
import com.ionelb.mymoviedb.paging.MoviesAdapter
import com.ionelb.mymoviedb.utils.Constants
import com.ionelb.mymoviedb.utils.isInternetAvailable
import com.ionelb.mymoviedb.viewmodel.MoviesViewModel
import com.ionelb.mymoviedb.viewmodel.OfflineMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    MoviesAdapter.OnItemClickListener,
    OfflineMoviesAdapter.OnOfflineItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MoviesViewModel
    private lateinit var offlineMoviesViewModel: OfflineMoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_tmdb)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.actionbar_title)

        /**
         * boolean variable that is used in deciding to save data to local database or not.
         * If false (when app is started), it saves the top 10 movies to the database. After this
         * it is set to true so no saving data to the database is done until the next start of the
         * application. It is updated using the LiveData so that the value is preserved during
         * the lifecycle of the application ends.
         */
        var toSave = false

        val adapter = MoviesAdapter(this)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        offlineMoviesViewModel = ViewModelProvider(this)[OfflineMoviesViewModel::class.java]

        /**
         * Checks whether the network capabilities are present and internet is available.
         * If internet no network capability is available, it loads data from the local database and
         * presents a notification to the user about the above situation.
         */
        if (!isInternetAvailable(this)) {
            recyclerView.visibility = View.GONE
            val offlineRecyclerView = findViewById<RecyclerView>(R.id.offline_recycler_view)
            offlineRecyclerView.setHasFixedSize(true)
            offlineRecyclerView.visibility = View.VISIBLE
            val offlineAdapter = OfflineMoviesAdapter(this)
            offlineRecyclerView.adapter = offlineAdapter
            offlineMoviesViewModel.getOfflineMovies().observe(this, {
                offlineAdapter.setOfflineMovies(it)
            })

            Snackbar.make(
                findViewById(R.id.myCoordinatorLayout),
                "No internet available. Loading cached data.",
                Snackbar.LENGTH_LONG
            ).show()
            recyclerView.visibility = View.GONE
        }

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]


        viewModel.getToSave().observe(this, {
            toSave = it
        })

        viewModel.movies.observe(this, {
            adapter.submitData(this.lifecycle, it)

            if (!toSave) {
                if (adapter.snapshot().size == 0) {
                    CoroutineScope(Dispatchers.IO).launch {
                        delay(TimeUnit.SECONDS.toMillis(2))
                        withContext(Dispatchers.IO) {
                            if (adapter.snapshot().size != 0) {
                                offlineMoviesViewModel.deleteAllMovies()
                                for (i in 0..9) {
                                    adapter.snapshot()[i]?.let { it1 ->
                                        offlineMoviesViewModel.insertMovie(
                                            OfflineMovie(
                                                0,
                                                it1.id,
                                                it1.overview,
                                                it1.backdrop_path,
                                                it1.title,
                                                it1.release_date,
                                                it1.vote_average
                                            )
                                        )
                                    }
                                }
                                viewModel.setToSaved()
                            }
                        }
                    }
                } else {
                    offlineMoviesViewModel.deleteAllMovies()
                    for (i in 0..9) {
                        adapter.snapshot()[i]?.let { it1 ->
                            offlineMoviesViewModel.insertMovie(
                                OfflineMovie(
                                    0,
                                    it1.id,
                                    it1.overview,
                                    it1.backdrop_path,
                                    it1.title,
                                    it1.release_date,
                                    it1.vote_average
                                )
                            )
                        }
                    }
                    viewModel.setToSaved()
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu?.findItem(R.id.search_button)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    recyclerView.scrollToPosition(0)
                    viewModel.searchMovie(query)

                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val image = Constants.IMAGE_BASE_URL + movie.backdrop_path
        intent.putExtra("thePicture", image)
        intent.putExtra("theTitle", movie.title)
        intent.putExtra("theReleaseDate", movie.release_date)
        intent.putExtra("theDescription", movie.overview)
        startActivity(intent)
    }

    override fun onOfflineItemClick(movie: OfflineMovie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val image = Constants.IMAGE_BASE_URL + movie.backdrop_path
        intent.putExtra("thePicture", image)
        intent.putExtra("theTitle", movie.title)
        intent.putExtra("theReleaseDate", movie.release_date)
        intent.putExtra("theDescription", movie.overview)
        startActivity(intent)
    }
}