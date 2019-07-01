package com.uipath.seamlessboard

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import com.uipath.seamlessboard.presentation.views.RatingView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object CustomMatchers {

    fun hasTextInputLayoutErrorText(expectedErrorText: String?): Matcher<View> = object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description?) {}

        override fun matchesSafely(item: View?): Boolean {
            if (item !is TextInputLayout) return false
            val error = item.error
            val errorString = error?.toString()
            return expectedErrorText == errorString
        }
    }

    fun checkRatingView(expectedRating: Float): Matcher<View> = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {}

        override fun matchesSafely(item: View?): Boolean {
            if (item !is RatingView) return false
            val rating = item.rating
            return expectedRating == rating
        }
    }
}