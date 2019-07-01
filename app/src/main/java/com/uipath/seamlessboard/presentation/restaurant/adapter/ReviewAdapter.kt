package com.uipath.seamlessboard.presentation.restaurant.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.uipath.seamlessboard.database.entity.Review

class ReviewAdapter : PagedListAdapter<Review, ReviewViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReviewViewHolder.create(parent)

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        getItem(position)?.let { review ->
            holder.bind(review)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Review, newItem: Review) =
                oldItem == newItem
        }
    }
}