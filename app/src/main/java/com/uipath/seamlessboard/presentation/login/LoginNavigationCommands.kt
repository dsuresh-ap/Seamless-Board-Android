package com.uipath.seamlessboard.presentation.login

sealed class LoginNavigationCommands {

    object StartBoardActivity : LoginNavigationCommands()

    object StartLogin : LoginNavigationCommands()
}