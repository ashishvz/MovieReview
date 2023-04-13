package com.ashishvz.moviereview

import android.app.Application
import com.ashishvz.moviereview.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            androidLogger()
            modules(
                listOf(
                    networkModule,
                    pagingModule,
                    dataSourceModule,
                    repositoryModule,
                    viewmodelModule
                )
            )
        }
    }
}