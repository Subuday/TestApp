package com.muzzlyworld.testapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muzzlyworld.testapp.databinding.ItemLoadingRowBinding

private const val SHOW_LOADING_ITEM = 1
private const val HIDE_LOADING_ITEM = 0

private const val LOADING_ITEM_POSITION = 0

class LoadingAdapter : RecyclerView.Adapter<LoadingViewHolder>() {

    var isLoading: Boolean = false
        set(isLoading) {
            if(field != isLoading) {
                if(isLoading) notifyItemInserted(LOADING_ITEM_POSITION)
                else notifyItemRemoved(LOADING_ITEM_POSITION)
                field = isLoading
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadingViewHolder
        = LoadingViewHolder.create(parent)

    override fun onBindViewHolder(holder: LoadingViewHolder, position: Int) {}

    override fun getItemCount(): Int = if(isLoading) SHOW_LOADING_ITEM else HIDE_LOADING_ITEM
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