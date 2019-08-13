package com.uipath.seamlessboard.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.uipath.seamlessboard.repository.UserRepository
import com.uipath.seamlessboard.utils.SingleLiveEvent

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val setContentViewLiveData = SingleLiveEvent<Unit>()
    fun setContentViewLiveData(): LiveData<Unit> = setContentViewLiveData

    private val navigationLiveData = SingleLiveEvent<LoginNavigationCommands>()
    fun navigationLiveData(): LiveData<LoginNavigationCommands> = navigationLiveData

    fun onCreate() {
        if (userRepository.hasUser()) {
            navigationLiveData.value = LoginNavigationCommands.StartBoardActivity
        } else {
            setContentViewLiveData.value = Unit
        }
    }

    fun onLoginClicked() {
        navigationLiveData.value = LoginNavigationCommands.StartLogin
    }

    fun onLoggedIn() {
        navigationLiveData.value = LoginNavigationCommands.StartBoardActivity
    }
}