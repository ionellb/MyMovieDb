package com.ionelb.mymoviedb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ionelb.mymoviedb.R

/**
 * Presents the detailed movie data received through an intent to the user.
 */
class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        supportActionBar?.title = getString(R.string.movie_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val image = findViewById<ImageView>(R.id.image)
        val titleText = findViewById<TextView>(R.id.movie_title)
        val releaseDateText = findViewById<TextView>(R.id.release_date)
        val descriptionText = findViewById<TextView>(R.id.movie_description)

        Glide.with(this)
            .load(intent.getStringExtra("thePicture").toString())
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(image)

        titleText.text = intent.getStringExtra("theTitle").toString()
        releaseDateText.text = intent.getStringExtra("theReleaseDate").toString()
        descriptionText.text = intent.getStringExtra("theDescription").toString()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id ==  android.R.id.home) {
            super.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}