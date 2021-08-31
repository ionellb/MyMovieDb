package com.ionelb.mymoviedb.data.local

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ionelb.mymoviedb.R
import com.ionelb.mymoviedb.data.model.OfflineMovie
import com.ionelb.mymoviedb.utils.Constants

/**
 * Adapter class [RecyclerView.Adapter] for [RecyclerView] which binds Movies along with [OfflineMoviesViewHolder]
 */
class OfflineMoviesAdapter(private val listener: OnOfflineItemClickListener) :
    RecyclerView.Adapter<OfflineMoviesAdapter.OfflineMoviesViewHolder>() {

    private var movies = emptyList<OfflineMovie>()

    inner class OfflineMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = movies[position]
                    listener.onOfflineItemClick(item)
                }
            }
        }

        val movieImage: ImageView = itemView.findViewById(R.id.movie_image)
        val titleText: TextView = itemView.findViewById(R.id.title)
        val ratingText: TextView = itemView.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfflineMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return OfflineMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: OfflineMoviesViewHolder, position: Int) {
        val currentItem = movies[position]
        val releaseDate = if (currentItem.release_date.length > 0) {
            " " + "(" + currentItem.release_date.substring(0, 4) + ")"
        } else ""
        val titleFinal =
            currentItem.title + releaseDate
        val ratingFinal = "\u2B50" + " " + currentItem.vote_average
        holder.titleText.text = titleFinal
        holder.ratingText.text = ratingFinal
        Glide.with(holder.itemView.context)
            .load("${Constants.IMAGE_BASE_URL}${currentItem.backdrop_path}")
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(holder.movieImage)
    }

    override fun getItemCount() = movies.size

    interface OnOfflineItemClickListener {
        fun onOfflineItemClick(movie: OfflineMovie)
    }

    /**
     * Method that adds OfflineMovie items (stored in the Room database)
     * to the initially empty list - movies
     */
    internal fun setOfflineMovies(movies: List<OfflineMovie>) {
        this.movies = movies
    }

}