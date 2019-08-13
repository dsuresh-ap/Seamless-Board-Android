package com.uipath.seamlessboard.presentation.board

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.uipath.seamlessboard.injection.appModule
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.injection.roomModule
import com.uipath.seamlessboard.presentation.addreview.mockApplication
import org.junit.*
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class BoardViewModelTest : KoinTest {

    private val boardViewModel by inject<BoardViewModel>()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        startKoin {
            modules(listOf(appModule, repoModule, roomModule, mockApplication))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun showSignOut_whenLogOutClicked() {
        boardViewModel.onLogoutClicked()
        Assert.assertEquals(BoardNavigationCommands.SignOut, boardViewModel.navigationLiveData().value)
    }

    @Test
    fun showLogin_whenLoggedOut() {
        boardViewModel.onLoggedOut()
        Assert.assertEquals(BoardNavigationCommands.Login, boardViewModel.navigationLiveData().value)
    }
}