package com.example.squaretest

import android.app.Application
import com.example.squaretest.koin.apiModule
import com.example.squaretest.koin.appModule
import com.example.squaretest.koin.repositoryModule
import com.example.squaretest.koin.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin and Load Core Modules
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)

            modules(
                listOf(
                    appModule,
                    apiModule,
                    repositoryModule,
                    viewModule
                )
            )
        }
    }

}