package org.onedroid.seefood.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.onedroid.seefood.app.di.initKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}