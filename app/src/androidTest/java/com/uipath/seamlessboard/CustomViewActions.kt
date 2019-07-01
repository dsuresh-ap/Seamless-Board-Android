package com.uipath.seamlessboard

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.uipath.seamlessboard.presentation.views.RatingView

object CustomViewActions {

    fun setRating(rating: Float) = object : ViewAction {
        override fun getDescription() = "Set rating on RatingView"

        override fun getConstraints() = isAssignableFrom(RatingView::class.java)

        override fun perform(uiController: UiController?, view: View?) {
            if (view is RatingView) {
                view.rating = rating
            }
        }

    }
}