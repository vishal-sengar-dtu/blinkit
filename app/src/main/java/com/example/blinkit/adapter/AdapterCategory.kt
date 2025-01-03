package com.example.blinkit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blinkit.databinding.ItemViewProductCategoryBinding
import com.example.blinkit.model.Category

class AdapterCategory(
    private val categoryList : List<Category>
) : RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>() {

    inner class CategoryViewHolder(
        private val binding : ItemViewProductCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            this.binding.apply {
                Glide.with(this.imgCategory)
                    .load(categoryList[position].imageUrl)
                    .override(100, 100)
                    .into(this.imgCategory)
                tvTitle.text = categoryList[position].title
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = ItemViewProductCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterCategory.CategoryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}