package com.muzzlyworld.testapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muzzlyworld.testapp.R
import com.muzzlyworld.testapp.core.Movie
import com.muzzlyworld.testapp.databinding.ItemMovieBinding
import com.muzzlyworld.testapp.utils.loadImage

class MovieAdapter(
    private val onMovieClickListener: MovieClickListener
) : ListAdapter<Movie, MovieViewHolder>(diffCallback) {

    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder.create(parent, onMovieClickListener)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_movie

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}

private typealias MovieClickListener = (Int) -> Unit

class MovieViewHolder private constructor(
    private val binding: ItemMovieBinding,
    private val clickListener: MovieClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Movie) {
        binding.poster.loadImage(item.poster)
        binding.name.text = item.name
        binding.description.text = item.description
        binding.layout.setOnClickListener { clickListener(item.id) }
    }

    companion object {
        fun create(parent: ViewGroup, clickListener: MovieClickListener): MovieViewHolder {
            val binding =
                ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding, clickListener)
        }
    }

}