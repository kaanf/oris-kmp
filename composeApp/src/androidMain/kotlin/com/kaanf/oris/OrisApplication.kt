package com.kaanf.oris

import android.app.Application
import com.kaanf.oris.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class OrisApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@OrisApplication)
            androidLogger()
        }
    }
}