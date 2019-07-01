package com.uipath.seamlessboard.repository

import androidx.annotation.VisibleForTesting
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.withTransaction
import com.uipath.seamlessboard.database.BoardDatabase
import com.uipath.seamlessboard.database.dao.RestaurantDao
import com.uipath.seamlessboard.database.entity.Restaurant
import com.uipath.seamlessboard.database.entity.RestaurantRating
import com.uipath.seamlessboard.database.entity.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface RestaurantRepository {
    fun getRestaurantRatings(): DataSource.Factory<Int, RestaurantRating>

    fun getRestaurantReviews(restaurantId: Long): DataSource.Factory<Int, Review>

    fun getRestaurantRating(restaurantId: Long): LiveData<RestaurantRating>

    @WorkerThread
    suspend fun insertRestaurantAndReview(restaurant: Restaurant, review: Review)

    @WorkerThread
    suspend fun insertRestaurantIfNeeded(restaurant: Restaurant): Long

    @VisibleForTesting
    @WorkerThread
    suspend fun findRestaurantByName(name: String): Restaurant?

    @WorkerThread
    suspend fun insertReview(review: Review): Long

    @VisibleForTesting
    @WorkerThread
    suspend fun findReviewsByRestaurantId(restaurantId: Long): List<Review>
}

class RestaurantRepositoryImpl(
    private val db: BoardDatabase,
    private val restaurantDao: RestaurantDao
) : RestaurantRepository {

    override fun getRestaurantRatings() = restaurantDao.getRestaurantRatingsDataSource()

    override fun getRestaurantReviews(restaurantId: Long) = restaurantDao.getReviewsDataSource(restaurantId)

    override fun getRestaurantRating(restaurantId: Long) = restaurantDao.getRestaurantRating(restaurantId)

    override suspend fun insertRestaurantAndReview(restaurant: Restaurant, review: Review) {
        withContext(Dispatchers.IO) {
            db.withTransaction {
                restaurant.id = insertRestaurantIfNeeded(restaurant)
                insertReview(review.apply { restaurantId = restaurant.id })
            }
        }
    }

    @WorkerThread
    override suspend fun insertRestaurantIfNeeded(restaurant: Restaurant): Long {
        val existingRestaurant = restaurantDao.getRestaurantByName(restaurant.name)
        return existingRestaurant?.id ?: restaurantDao.insert(restaurant)
    }

    @VisibleForTesting
    @WorkerThread
    override suspend fun findRestaurantByName(name: String) = restaurantDao.getRestaurantByName(name)

    @WorkerThread
    override suspend fun insertReview(review: Review) = restaurantDao.insert(review)

    @VisibleForTesting
    @WorkerThread
    override suspend fun findReviewsByRestaurantId(restaurantId: Long) =
        restaurantDao.getReviewsByRestaurantId(restaurantId)
}