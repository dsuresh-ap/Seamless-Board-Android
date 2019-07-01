package com.uipath.seamlessboard.presentation.addreview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.injection.appModule
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.injection.roomTestModule
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(JUnit4::class)
class AddReviewViewModelTest : KoinTest {

    private val addReviewViewModel by inject<AddReviewViewModel>()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(appModule, repoModule, roomTestModule, mockApplication))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun showErrors_withNoInput_whenValidationFailed() {
        addReviewViewModel.validateReview()

        assertEquals(R.string.add_review_restaurant_name_error, addReviewViewModel.restaurantNameError.value)
        assertEquals(R.string.add_review_restaurant_review_error, addReviewViewModel.reviewError.value)
    }

    @Test
    fun showNoErrors_withInput_whenValidationSucceeded() {
        addReviewViewModel.restaurantName.value = "Curry Heights"
        addReviewViewModel.review.value = "Very good place"
        addReviewViewModel.validateReview()

        assertNull(addReviewViewModel.restaurantNameError.value)
        assertNull(addReviewViewModel.reviewError.value)
    }
}