package com.example.jokes

import android.app.Application
import com.example.jokes.di.networkModule
import com.example.jokes.di.repositoryModule
import com.example.jokes.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JokesApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JokesApp)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}
