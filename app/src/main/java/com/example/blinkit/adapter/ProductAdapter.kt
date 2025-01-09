package com.example.blinkit.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blinkit.ProductFilter
import com.example.blinkit.R
import com.example.blinkit.Utility
import com.example.blinkit.databinding.ProuctItemViewBinding
import com.example.blinkit.model.Product

class ProductAdapter(
    private val fragment : Fragment,
    val onAddButtonClick : (Product, ProuctItemViewBinding) -> Unit,
    val onIncrementButtonClick : (Product, ProuctItemViewBinding) -> Unit,
    val onDecrementButtonClick : (Product, ProuctItemViewBinding) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable {

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Product>() {
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        return ProductViewHolder(ProuctItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ProductViewHolder(val binding : ProuctItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val product = differ.currentList[position]

            binding.apply {
                if(product.itemCount == 0) {
                    btnAdd.visibility = View.VISIBLE
                    btnQuantity.visibility = View.GONE
                } else {
                    btnAdd.visibility = View.GONE
                    btnQuantity.visibility = View.VISIBLE
                    tvProductQuantity.text = product.itemCount.toString()
                }


                val quantity = "${product.quantity} ${product.unit}"
                tvQuantity.text = quantity

                tvName.text = product.title

                if(product.discount == null || product.discount == 0) {
                    tvDiscount.visibility = View.GONE
                    tvMrp.visibility = View.GONE
                } else {
                    tvDiscount.visibility = View.VISIBLE
                    tvMrp.visibility = View.VISIBLE
                    tvDiscount.text = fragment.getString(R.string.discount_text, product.discount)
                    val builder = SpannableStringBuilder()
                    builder.append("MRP ")
                    val start = builder.length
                    builder.append("₹${Utility.priceString(product.price.toString())}")
                        .setSpan(StrikethroughSpan(), start, builder.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    tvMrp.text = builder
                }

                tvPrice.text = fragment.getString(R.string.price_text, Utility.priceString(Utility.discountPrice(product.price!!, product.discount).toString()))

                // Set the initial image
                Glide.with(imageSwitcher)
                    .load(product.productImageUrl!![0])
                    .override(100, 100)
                    .into(imageView)

                btnAdd.setOnClickListener {
                    onAddButtonClick(product, binding)

                    btnPlus.setOnClickListener {
                        onIncrementButtonClick(product, binding)
                    }

                    btnMinus.setOnClickListener {
                        onDecrementButtonClick(product, binding)
                    }

                }
            }

        }

    }

    var originalProductList : List<Product> = ArrayList()
    var filter : ProductFilter? = null
    override fun getFilter(): Filter {
        if(filter == null) {
            filter = ProductFilter(this, originalProductList as ArrayList<Product>)
        }
        return filter as ProductFilter
    }
}