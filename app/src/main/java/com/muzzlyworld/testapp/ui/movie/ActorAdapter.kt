package com.muzzlyworld.testapp.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muzzlyworld.testapp.core.Actor
import com.muzzlyworld.testapp.databinding.ItemActorBinding
import com.muzzlyworld.testapp.utils.loadImage

class ActorAdapter : ListAdapter<Actor, ActorViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder =
        ActorViewHolder.create(parent)

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Actor>() {
            override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean = oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean = oldItem == newItem
        }
    }


}

class ActorViewHolder private constructor(
    private val binding: ItemActorBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Actor) {
        binding.character.text = item.character
        binding.name.text = item.name
        binding.image.loadImage(item.image)
    }

    companion object {
        fun create(parent: ViewGroup) : ActorViewHolder {
            val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ActorViewHolder(binding)
        }
    }

}