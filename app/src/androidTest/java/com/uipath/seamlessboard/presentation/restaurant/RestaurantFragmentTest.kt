package com.uipath.seamlessboard.presentation.restaurant

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.room.withTransaction
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.uipath.seamlessboard.CustomMatchers
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.RecyclerViewItemCountAssertion
import com.uipath.seamlessboard.database.BoardDatabase
import com.uipath.seamlessboard.database.entity.Restaurant
import com.uipath.seamlessboard.database.entity.Review
import com.uipath.seamlessboard.injection.appModule
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.injection.roomTestModule
import com.uipath.seamlessboard.repository.RestaurantRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito


@MediumTest
@RunWith(AndroidJUnit4::class)
class RestaurantFragmentTest : KoinTest {

    private val db by inject<BoardDatabase>()
    private val repository by inject<RestaurantRepository>()

    @Before
    fun setUp() {
        Intents.init()
        loadKoinModules(listOf(appModule, repoModule, roomTestModule))
    }

    @After
    fun tearDown() {
        Intents.release()
        unloadKoinModules(listOf(appModule, repoModule, roomTestModule))
    }

    @Test
    fun showRestaurant_whenScreenIsLoaded() {
        runBlocking {
            var restaurantId = 0L

            db.withTransaction {
                restaurantId = repository.insertRestaurantIfNeeded(Restaurant("Curry Heights"))
                repository.insertReview(Review(2.5f, 3f, "This is ok food.").apply { this.restaurantId = restaurantId })
                repository.insertReview(Review(4f, 4f, "This is ok food.").apply { this.restaurantId = restaurantId })
            }

            val args = Bundle().apply {
                putLong("restaurantId", restaurantId)
            }
            val mockNavController = Mockito.mock(NavController::class.java)
            val restaurantScenario =
                launchFragmentInContainer<RestaurantFragment>(fragmentArgs = args, themeResId = R.style.AppTheme)
            restaurantScenario.onFragment {
                Navigation.setViewNavController(it.requireView(), mockNavController)
//                assertEquals("Curry Heights", (it.activity as? AppCompatActivity)?.supportActionBar?.title)
            }

            Espresso.onView(withId(R.id.foodRatingView))
                .check(ViewAssertions.matches(CustomMatchers.checkRatingView(3f)))
            Espresso.onView(withId(R.id.deliveryRatingView))
                .check(ViewAssertions.matches(CustomMatchers.checkRatingView(4f)))
        }
    }

    @Test
    fun showReviews_whenScreenIsLoaded() {
        runBlocking {
            var restaurantId = 0L

            db.withTransaction {
                restaurantId = repository.insertRestaurantIfNeeded(Restaurant("Curry Heights"))
                repository.insertReview(Review(2.5f, 3f, "This is ok food.").apply { this.restaurantId = restaurantId })
                repository.insertReview(Review(4f, 4f, "This is ok food.").apply { this.restaurantId = restaurantId })
            }

            val args = Bundle().apply {
                putLong("restaurantId", restaurantId)
            }
            val mockNavController = Mockito.mock(NavController::class.java)
            val restaurantScenario =
                launchFragmentInContainer<RestaurantFragment>(fragmentArgs = args, themeResId = R.style.AppTheme)
            restaurantScenario.onFragment {
                Navigation.setViewNavController(it.requireView(), mockNavController)
            }

            Espresso.onView(withId(R.id.reviewRecyclerView))
                .check(RecyclerViewItemCountAssertion(2))
        }
    }
}