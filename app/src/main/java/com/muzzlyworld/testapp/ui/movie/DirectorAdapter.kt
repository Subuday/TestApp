package com.muzzlyworld.testapp.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muzzlyworld.testapp.core.Director
import com.muzzlyworld.testapp.databinding.ItemDirectorBinding
import com.muzzlyworld.testapp.utils.loadImage

class DirectorAdapter : ListAdapter<Director, DirectorViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectorViewHolder =
        DirectorViewHolder.create(parent)


    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Director>() {
            override fun areItemsTheSame(oldItem: Director, newItem: Director): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Director, newItem: Director): Boolean =
                oldItem == newItem
        }
    }
}

class DirectorViewHolder private constructor(
    private val binding: ItemDirectorBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Director) {
        binding.name.text = item.name
        binding.image.loadImage(item.image)
    }

    companion object {
        fun create(parent: ViewGroup): DirectorViewHolder {
            val binding =
                ItemDirectorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DirectorViewHolder(binding)
        }
    }
}