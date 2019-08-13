package com.uipath.seamlessboard.presentation.board

sealed class BoardNavigationCommands {

    object StartAddReviewActivity : BoardNavigationCommands()

    data class ShowRestaurantFragment(val restaurantId: Long) : BoardNavigationCommands()

    object SignOut : BoardNavigationCommands()

    object Login : BoardNavigationCommands()
}