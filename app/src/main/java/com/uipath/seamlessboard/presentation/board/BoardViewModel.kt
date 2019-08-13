package com.uipath.seamlessboard.presentation.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.uipath.seamlessboard.database.entity.RestaurantRating
import com.uipath.seamlessboard.repository.RestaurantRepository
import com.uipath.seamlessboard.repository.UserRepository
import com.uipath.seamlessboard.utils.SingleLiveEvent

class BoardViewModel(restaurantRepository: RestaurantRepository, userRepository: UserRepository) : ViewModel() {

    private val navigationLiveData = SingleLiveEvent<BoardNavigationCommands>()
    fun navigationLiveData(): LiveData<BoardNavigationCommands> = navigationLiveData
    val restaurantPagedList = restaurantRepository.getRestaurantRatings().toLiveData(50)

    fun onAddReviewClicked() {
        navigationLiveData.value = BoardNavigationCommands.StartAddReviewActivity
    }

    fun onRestaurantRatingClicked(rating: RestaurantRating) {
        navigationLiveData.value = BoardNavigationCommands.ShowRestaurantFragment(rating.restaurant.id)
    }

    fun onLogoutClicked() {
        navigationLiveData.value = BoardNavigationCommands.SignOut
    }

    fun onLoggedOut() {
        navigationLiveData.value = BoardNavigationCommands.Login
    }
}