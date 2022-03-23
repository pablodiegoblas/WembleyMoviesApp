package com.example.wembleymoviesapp.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wembleymoviesapp.BuildConfig
import com.example.wembleymoviesapp.R
import com.example.wembleymoviesapp.databinding.ItemMovieBinding
import com.example.wembleymoviesapp.domain.MovieItem
import com.squareup.picasso.Picasso

class FavMoviesAdapter(
    private val onMoreClick: (MovieItem) -> Unit,
    private val onFavouriteClick: (favourite: MovieItem) -> Unit,
    private val onSharedClick: (MovieItem) -> Unit
) : ListAdapter<MovieItem, FavMoviesAdapter.ViewHolder>(DiffUtilCallBackFavourite()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        //Charge the elements directly with view binding
        fun bindMovie(movieItem: MovieItem) {
            with(movieItem) {
                poster?.let { loadImage(it, binding.imageViewMovie) }
                binding.textViewTitleMovie.text = title
                //Change the fav image
                if (favourite) binding.imageViewFavourite.setImageResource(R.drawable.ic_favourite)
                else binding.imageViewFavourite.setImageResource(R.drawable.ic_no_favourite_red)
            }

            binding.buttonMore.setOnClickListener {
                onMoreClick(movieItem)
            }
            binding.imageViewFavourite.setOnClickListener {
                onFavouriteClick(movieItem)
            }
            binding.imageViewShared.setOnClickListener {
                onSharedClick(movieItem)
            }
        }

        private fun loadImage(url: String, imageView: ImageView) =
            Picasso.get().load("${BuildConfig.ApiImagesUrl}$url").fit().into(imageView)

    }

}

private class DiffUtilCallBackFavourite : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem == newItem

}