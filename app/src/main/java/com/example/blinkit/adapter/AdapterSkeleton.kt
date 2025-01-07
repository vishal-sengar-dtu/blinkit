package com.example.blinkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blinkit.databinding.SkeletonLoaderItemBinding
import com.example.blinkit.model.Product

class AdapterSkeleton(private val skeletonList : List<String>) : RecyclerView.Adapter<AdapterSkeleton.SkeletonViewHolder>() {

    inner class SkeletonViewHolder(
        private val binding : SkeletonLoaderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            this.binding.apply {
                shimmer.showShimmer(true)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterSkeleton.SkeletonViewHolder {
        val binding = SkeletonLoaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SkeletonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterSkeleton.SkeletonViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return skeletonList.size
    }
}