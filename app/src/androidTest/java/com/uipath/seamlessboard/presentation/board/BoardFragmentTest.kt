package com.uipath.seamlessboard.presentation.board

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.room.withTransaction
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.database.BoardDatabase
import com.uipath.seamlessboard.database.entity.Restaurant
import com.uipath.seamlessboard.database.entity.Review
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.injection.roomTestModule
import com.uipath.seamlessboard.presentation.board.adapter.RestaurantViewHolder
import com.uipath.seamlessboard.repository.RestaurantRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@RunWith(AndroidJUnit4::class)
class BoardFragmentTest : KoinTest {

    private val db by inject<BoardDatabase>()
    private val repository by inject<RestaurantRepository>()

    @Before
    fun setUp() {
        Intents.init()
        loadKoinModules(listOf(repoModule, roomTestModule))
    }

    @After
    fun tearDown() {
        Intents.release()
        unloadKoinModules(listOf(repoModule, roomTestModule))
    }

    @Test
    fun showEmptyState_whenNoReviewsAndScreenLoaded() {
        launchFragmentInContainer<BoardFragment>(themeResId = R.style.AppTheme)
        onView(withId(R.id.emptyStateTextView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun hideEmptyState_whenReviewsAndScreenLoaded() {
        runBlocking {
            repository.insertRestaurantAndReview(
                Restaurant("Curry Heights"),
                Review(2.5f, 3f, "This is ok food.")
            )

            val mockNavController = mock(NavController::class.java)
            val boardScenario = launchFragmentInContainer<BoardFragment>(themeResId = R.style.AppTheme)
            boardScenario.onFragment {
                Navigation.setViewNavController(it.requireView(), mockNavController)
            }

            delay(500)
            onView(withId(R.id.emptyStateTextView))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        }
    }

    @Test
    fun showRestaurantDetail_whenRestaurantRatingClicked() {
        var restaurantId = 0L
        runBlocking {
            db.withTransaction {
                val review = Review(4f, 3f, "This is ok food.")
                restaurantId = repository.insertRestaurantIfNeeded(Restaurant("Curry Heights"))
                review.restaurantId = restaurantId
                repository.insertReview(review)
            }
        }

        val mockNavController = mock(NavController::class.java)
        val boardScenario = launchFragmentInContainer<BoardFragment>(themeResId = R.style.AppTheme)
        boardScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }
        onView(withId(R.id.boardRecyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RestaurantViewHolder>(0, click()))
        verify(mockNavController).navigate(BoardFragmentDirections.actionBoardFragmentToRestaurantFragment(restaurantId))
    }

    @Test
    fun showAddReviewScreen_whenFabClicked() {
        val mockNavController = mock(NavController::class.java)
        val boardScenario = launchFragmentInContainer<BoardFragment>(themeResId = R.style.AppTheme)
        boardScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), mockNavController)
        }
        onView(withId(R.id.addReviewFab))
            .perform(click())
        verify(mockNavController).navigate(BoardFragmentDirections.actionBoardFragmentToAddReviewActivity())
    }
}