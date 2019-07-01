package com.uipath.seamlessboard.presentation.addreview

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uipath.seamlessboard.R
import com.uipath.seamlessboard.database.entity.Restaurant
import com.uipath.seamlessboard.database.entity.Review
import com.uipath.seamlessboard.repository.RestaurantRepository
import com.uipath.seamlessboard.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class AddReviewViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {

    private val navigationLiveData = SingleLiveEvent<AddReviewNavigationCommand>()
    fun navigationLiveData(): LiveData<AddReviewNavigationCommand> = navigationLiveData

    val restaurantName = MutableLiveData<String>()
    val restaurantNameError = MutableLiveData<@androidx.annotation.StringRes Int?>()

    val deliveryRating = MutableLiveData<Float>()
    val foodRating = MutableLiveData<Float>()

    val review = MutableLiveData<String>()
    val reviewError = MutableLiveData<@androidx.annotation.StringRes Int?>()

    fun onSaveClicked() {
        val hasErrors = validateReview()
        if (!hasErrors) {
            viewModelScope.launch {
                restaurantRepository.insertRestaurantAndReview(
                    Restaurant(restaurantName.value!!),
                    Review(foodRating.value ?: 1f, deliveryRating.value ?: 1f, review.value!!)
                )
                navigationLiveData.value = AddReviewNavigationCommand.FinishActivity
            }
        }
    }

    @VisibleForTesting
    fun validateReview(): Boolean {
        var hasErrors = false
        val restaurantName = restaurantName.value
        if (restaurantName.isNullOrBlank()) {
            restaurantNameError.value = R.string.add_review_restaurant_name_error
            hasErrors = true
        } else {
            restaurantNameError.value = null
        }

        val review = review.value
        if (review.isNullOrBlank()) {
            reviewError.value = R.string.add_review_restaurant_review_error
            hasErrors = true
        } else {
            reviewError.value = null
        }

        return hasErrors
    }
}