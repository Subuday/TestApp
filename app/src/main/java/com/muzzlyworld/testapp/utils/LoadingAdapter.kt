package com.muzzlyworld.testapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.muzzlyworld.testapp.databinding.ItemLoadingRowBinding

class LoadingAdapter : ListAdapter<Unit, LoadingViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder
        = LoadingViewHolder.create(parent)

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {}

    private companion object {
        val diffCallback = object  : DiffUtil.ItemCallback<Unit>() {
            override fun areItemsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
            override fun areContentsTheSame(oldItem: Unit, newItem: Unit): Boolean = true
        }

    }
}

class LoadingViewHolder private constructor(
    binding: ItemLoadingRowBinding
) : RecyclerView.ViewHolder(binding.root) {


    companion object {
        fun create(parent: ViewGroup) : LoadingViewHolder {
            val binding = ItemLoadingRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return LoadingViewHolder(binding)
        }
    }


}