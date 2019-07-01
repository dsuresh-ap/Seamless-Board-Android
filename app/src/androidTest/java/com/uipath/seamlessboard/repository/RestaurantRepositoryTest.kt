package com.uipath.seamlessboard.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.uipath.seamlessboard.database.entity.Restaurant
import com.uipath.seamlessboard.database.entity.Review
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.injection.roomTestModule
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

@MediumTest
@RunWith(AndroidJUnit4::class)
class RestaurantRepositoryTest : KoinTest {

    private val repository by inject<RestaurantRepository>()

    @Before
    fun setUp() {
        loadKoinModules(listOf(repoModule, roomTestModule))
    }

    @After
    fun tearDown() {
        unloadKoinModules(listOf(repoModule, roomTestModule))
    }

    @Test
    fun testInsertRestaurant_shouldInsert() = runBlocking {
        val restaurant = Restaurant("Curry Heights")
        repository.insertRestaurantIfNeeded(restaurant)

        val restaurantByName = repository.findRestaurantByName(restaurant.name)
        assertThat(restaurant, equalTo(restaurantByName))
    }

    @Test
    fun testInsertReview_shouldInsert() = runBlocking {
        val restaurant = Restaurant("Curry Heights")
        val review = Review(4f, 3f, "This is the best food")
        repository.insertRestaurantAndReview(restaurant, review)

        val reviewsById = repository.findReviewsByRestaurantId(restaurant.id)
        val containsReview = reviewsById.any {
            it.restaurantId == restaurant.id &&
                    it.foodRating == 4f &&
                    it.deliveryRating == 3f &&
                    it.review == "This is the best food"
        }
        assertThat(containsReview, equalTo(true))
    }
}