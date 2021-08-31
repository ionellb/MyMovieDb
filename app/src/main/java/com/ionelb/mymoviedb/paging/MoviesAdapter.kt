package com.ionelb.mymoviedb.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ionelb.mymoviedb.R
import com.ionelb.mymoviedb.data.model.Movie
import com.ionelb.mymoviedb.utils.Constants

/**
 * Adapter class [PagingDataAdapter] for [RecyclerView] which binds Movies along with [MoviesViewHolder]
 */
class MoviesAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(COMPARATOR) {

    inner class MoviesViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        val movieImage: ImageView = view.findViewById(R.id.movie_image)
        val titleText: TextView = view.findViewById(R.id.title)
        val ratingText: TextView = view.findViewById(R.id.rating)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MoviesViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem

        }
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

}