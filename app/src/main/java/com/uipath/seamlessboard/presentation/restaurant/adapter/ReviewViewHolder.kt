package com.uipath.seamlessboard.presentation.restaurant.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.database.entity.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup) = ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        )
    }

    fun bind(review: Review) {
        itemView.foodRatingBar.rating = review.foodRating
        itemView.deliveryRatingBar.rating = review.deliveryRating
        itemView.reviewTextView.text = review.review
    }
}