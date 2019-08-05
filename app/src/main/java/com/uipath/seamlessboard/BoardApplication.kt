package com.uipath.seamlessboard

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.uipath.seamlessboard.injection.appModule
import com.uipath.seamlessboard.injection.repoModule
import com.uipath.seamlessboard.injection.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BoardApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        startKoin {
            androidLogger()
            androidContext(this@BoardApplication)
            modules(listOf(appModule, repoModule, roomModule))
        }
    }
}