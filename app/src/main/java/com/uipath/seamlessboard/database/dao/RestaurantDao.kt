package com.uipath.seamlessboard.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.uipath.seamlessboard.database.entity.Restaurant
import com.uipath.seamlessboard.database.entity.RestaurantRating
import com.uipath.seamlessboard.database.entity.Review

@Dao
interface RestaurantDao {

    @Query("SELECT name FROM restaurants")
    fun getRestaurantNamesLiveData(): LiveData<List<String>>

    @Transaction
    @Query("SELECT *, AVG(rv.delivery_rating) delivery_rating, AVG(rv.food_rating) food_rating FROM restaurants rs LEFT JOIN reviews rv ON rs.restaurant_id = rv.restaurant_id GROUP BY rs.restaurant_id ORDER BY rs.name")
    fun getRestaurantRatingsDataSource(): DataSource.Factory<Int, RestaurantRating>

    @Query("SELECT * FROM reviews WHERE restaurant_id = :restaurantId")
    fun getReviewsDataSource(restaurantId: Long): DataSource.Factory<Int, Review>

    @Transaction
    @Query("SELECT *, AVG(rv.delivery_rating) delivery_rating, AVG(rv.food_rating) food_rating FROM restaurants rs LEFT JOIN reviews rv ON rs.restaurant_id = rv.restaurant_id WHERE rs.restaurant_id = :restaurantId GROUP BY rs.restaurant_id ORDER BY rs.name")
    fun getRestaurantRating(restaurantId: Long): LiveData<RestaurantRating>

    @Query("SELECT * FROM restaurants WHERE name = :name LIMIT 1")
    suspend fun getRestaurantByName(name: String): Restaurant?

    @Query("SELECT * FROM reviews WHERE restaurant_id = :restaurantId")
    suspend fun getReviewsByRestaurantId(restaurantId: Long): List<Review>

    @Insert
    suspend fun insert(restaurant: Restaurant): Long

    @Insert
    suspend fun insert(review: Review): Long
}