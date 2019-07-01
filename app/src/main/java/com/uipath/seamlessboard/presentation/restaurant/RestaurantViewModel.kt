package com.uipath.seamlessboard.presentation.restaurant

import androidx.lifecycle.ViewModel
import androidx.paging.toLiveData
import com.uipath.seamlessboard.repository.RestaurantRepository

class RestaurantViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {

    private var restaurantId = 0L

    fun reviewsPagedList() = restaurantRepository.getRestaurantReviews(restaurantId).toLiveData(50)
    fun restaurant() = restaurantRepository.getRestaurantRating(restaurantId)

    fun onCreate(restaurantId: Long) {
        this.restaurantId = restaurantId
    }
}