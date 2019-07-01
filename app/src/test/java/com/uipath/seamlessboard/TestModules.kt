package com.uipath.seamlessboard.presentation.addreview

import android.app.Application
import android.content.Context
import org.koin.dsl.module
import org.mockito.Mockito

val mockApplication = module(override = true) {
    single { Mockito.mock(Application::class.java) }
    single { Mockito.mock(Context::class.java) }
}