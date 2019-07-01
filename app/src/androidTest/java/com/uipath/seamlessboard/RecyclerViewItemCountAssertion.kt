package com.uipath.seamlessboard

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert.assertThat

class RecyclerViewItemCountAssertion(expectedCount: Int) : ViewAssertion {

    private val matcher: Matcher<Int> = `is`(expectedCount)

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as? RecyclerView
        val adapter = recyclerView?.adapter!!
        assertThat(adapter.itemCount, matcher)

    }
}