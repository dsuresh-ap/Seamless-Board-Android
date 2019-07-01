package com.uipath.seamlessboard.presentation.board.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.database.entity.RestaurantRating
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup) = RestaurantViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        )
    }

    fun bind(restaurantRating: RestaurantRating, onClickListener: View.OnClickListener) {
        itemView.setOnClickListener(onClickListener)
        itemView.nameTextView.text = restaurantRating.restaurant.name
        itemView.ratingTextView.text =
            itemView.context.getString(R.string.rating_format, restaurantRating.averageRating)
    }
}