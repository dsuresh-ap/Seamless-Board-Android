package com.uipath.seamlessboard.presentation.addreview

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.uipath.seamlessboard.CustomMatchers.hasTextInputLayoutErrorText
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.injection.roomTestModule
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest

@MediumTest
@RunWith(AndroidJUnit4::class)
class AddReviewActivityTest : KoinTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<AddReviewActivity>(AddReviewActivity::class.java)

    @Before
    fun setUp() {
        loadKoinModules(roomTestModule)
    }

    @Test
    fun showNameError_withNoName_whenSavedClicked() {
        onView(withId(R.id.saveFab))
            .perform(click())
        onView(withId(R.id.nameInputLayout))
            .check(matches(hasTextInputLayoutErrorText(activityRule.activity.getString(R.string.add_review_restaurant_name_error))))
    }

    @Test
    fun hideNameError_withName_whenSaveClicked() {
        onView(withId(R.id.nameAutoCompleteTextView))
            .perform(clearText(), typeText("Curry Heights"), closeSoftKeyboard())
        onView(withId(R.id.saveFab))
            .perform(click())
        onView(withId(R.id.nameInputLayout))
            .check(matches(hasTextInputLayoutErrorText(null)))
    }

    @Test
    fun showReviewError_withNoReview_whenSavedClicked() {
        onView(withId(R.id.saveFab))
            .perform(click())
        onView(withId(R.id.reviewInputLayout))
            .check(matches(hasTextInputLayoutErrorText(activityRule.activity.getString(R.string.add_review_restaurant_review_error))))
    }

    @Test
    fun hideReviewError_withReview_whenSaveClicked() {
        onView(withId(R.id.reviewEditText))
            .perform(clearText(), typeText("The best Indian food!"), closeSoftKeyboard())
        onView(withId(R.id.saveFab))
            .perform(click())
        onView(withId(R.id.reviewInputLayout))
            .check(matches(hasTextInputLayoutErrorText(null)))
    }

    @Test
    fun finishScreen_withReview_whenSaveClicked() {
        onView(withId(R.id.nameAutoCompleteTextView))
            .perform(clearText(), typeText("Curry Heights"), closeSoftKeyboard())
        onView(withId(R.id.reviewEditText))
            .perform(clearText(), typeText("The best Indian food!"), closeSoftKeyboard())
        onView(withId(R.id.saveFab))
            .perform(click())
        assertTrue(activityRule.activity.isFinishing)
    }
}