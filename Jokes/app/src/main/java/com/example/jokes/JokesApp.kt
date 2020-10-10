package com.example.jokes

import android.app.Application
import com.example.jokes.data.module.networkModule
import com.example.jokes.data.module.repositoryModule
import com.example.jokes.domain.module.useCaseModule
import com.example.jokes.presentation.module.mapperModule
import com.example.jokes.presentation.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class JokesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JokesApp)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule,
                useCaseModule,
                mapperModule
            )
        }
    }
}
