package com.example.lab_4

import android.app.Application
import com.example.lab_4.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LabApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LabApp)
            modules(appModule)
        }
    }
}