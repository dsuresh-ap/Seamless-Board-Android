package com.uipath.seamlessboard.presentation.board.adapter

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.uipath.seamlessboard.database.entity.RestaurantRating

class BoardAdapter(private val onRestaurantClickListener: (RestaurantRating) -> Unit) :
    PagedListAdapter<RestaurantRating, RestaurantViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RestaurantViewHolder.create(parent)

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        getItem(position)?.let { restaurant ->
            val onClickListener = View.OnClickListener {
                onRestaurantClickListener(restaurant)
            }
            holder.bind(restaurant, onClickListener)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<RestaurantRating>() {
            override fun areItemsTheSame(oldItem: RestaurantRating, newItem: RestaurantRating) =
                oldItem.restaurant.id == newItem.restaurant.id

            override fun areContentsTheSame(oldItem: RestaurantRating, newItem: RestaurantRating) =
                oldItem == newItem
        }
    }
}