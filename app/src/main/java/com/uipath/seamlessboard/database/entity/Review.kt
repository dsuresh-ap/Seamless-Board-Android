package com.uipath.seamlessboard.database.entity

import androidx.annotation.FloatRange
import androidx.room.*

@Entity(
    tableName = "reviews",
    foreignKeys = [ForeignKey(
        entity = Restaurant::class,
        parentColumns = ["restaurant_id"],
        childColumns = ["restaurant_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["restaurant_id"])]
)
data class Review(
    @ColumnInfo(name = "food_rating")
    @FloatRange(from = 1.0, to = 5.0)
    var foodRating: Float,
    @ColumnInfo(name = "delivery_rating")
    @FloatRange(from = 1.0, to = 5.0)
    var deliveryRating: Float,
    @ColumnInfo(name = "review")
    var review: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "review_id")
    var id: Long = 0

    @ColumnInfo(name = "restaurant_id")
    var restaurantId: Long = 0
}