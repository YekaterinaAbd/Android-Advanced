package com.example.jokes.di

import android.util.Log
import com.example.jokes.data.api.ApiClient
import com.example.jokes.data.api.JokeApi
import com.example.jokes.data.repository.JokesRepository
import com.example.jokes.data.repository.JokesRepositoryImpl
import com.example.jokes.utils.BASE_URL
import com.example.jokes.view_model.JokesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { ApiClient.createLoggingInterceptor() }
    single { ApiClient.createHttpClient(httpLoggingInterceptor = get()) }
    single { ApiClient.createApiService(okHttpClient = get()) }
}

val repositoryModule = module {
    single<JokesRepository>{
        JokesRepositoryImpl(service = get())
    }
}

val viewModelModule = module {
    viewModel {JokesViewModel(jokesRepository = get())}
}

