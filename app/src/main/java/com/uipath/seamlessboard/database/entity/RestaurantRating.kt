package com.uipath.seamlessboard.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class RestaurantRating(
    @Embedded
    val restaurant: Restaurant,
    @ColumnInfo(name = "delivery_rating")
    val deliveryRating: Float,
    @ColumnInfo(name = "food_rating")
    val foodRating: Float
) {
    val averageRating: Float
        get() = (deliveryRating + foodRating) / 2
}