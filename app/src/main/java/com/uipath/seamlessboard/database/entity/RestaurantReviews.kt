package com.uipath.seamlessboard.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RestaurantReviews(
    @Embedded
    val restaurant: Restaurant,
    @Relation(parentColumn = "restaurant_id", entityColumn = "restaurant_id", entity = Review::class)
    val reviews: List<Review>
)