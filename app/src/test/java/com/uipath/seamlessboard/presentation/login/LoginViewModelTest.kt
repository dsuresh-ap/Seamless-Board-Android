package com.uipath.seamlessboard.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uipath.seamlessboard.injection.appModule
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.presentation.addreview.mockApplication
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(JUnit4::class)
class LoginViewModelTest : KoinTest {

    private val loginViewModel by inject<LoginViewModel>()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(appModule, repoModule, mockApplication))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun showLogin_whenLoginClicked() {
        loginViewModel.onLoginClicked()
        Assert.assertEquals(LoginNavigationCommands.StartLogin, loginViewModel.navigationLiveData().value)
    }

    @Test
    fun showBoard_whenLoginSucceeded() {
        loginViewModel.onLoggedIn()
        Assert.assertEquals(LoginNavigationCommands.StartBoardActivity, loginViewModel.navigationLiveData().value)
    }
}