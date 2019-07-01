package com.uipath.seamlessboard.presentation.addreview

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.injection.roomTestModule
import com.uipath.seamlessboard.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class AddReviewViewModelUiTest : KoinTest {

    private val addReviewViewModel by inject<AddReviewViewModel>()
    private val restaurantRepository by inject<RestaurantRepository>()

    @Before
    fun setUp() {
        loadKoinModules(listOf(repoModule, roomTestModule))
    }

    @After
    fun tearDown() {
        unloadKoinModules(listOf(repoModule, roomTestModule))
    }

    @Test
    fun saveReview_noDuplicate_whenSaveClicked() = runBlocking {
        withContext(Dispatchers.Main) {
            addReviewViewModel.restaurantName.value = "Curry Heights"
            addReviewViewModel.foodRating.value = 4.5f
            addReviewViewModel.deliveryRating.value = 3.5f
            addReviewViewModel.review.value = "Very good food."
            addReviewViewModel.onSaveClicked()
        }

        delay(500)

        val restaurant = restaurantRepository.findRestaurantByName("Curry Heights")
        if (restaurant == null) {
            Assert.fail("Restaurant was null")
            return@runBlocking
        }

        val reviews = restaurantRepository.findReviewsByRestaurantId(restaurant.id)
        Assert.assertNotNull(reviews.firstOrNull { it.foodRating == 4.5f && it.deliveryRating == 3.5f && it.review == "Very good food." })
    }
}