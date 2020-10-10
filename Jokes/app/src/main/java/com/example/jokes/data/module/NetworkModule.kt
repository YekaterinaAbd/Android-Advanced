package com.example.jokes.data.module

import com.example.jokes.data.api.ApiClient
import org.koin.dsl.module

val networkModule = module {
    single { ApiClient.createLoggingInterceptor() }
    single { ApiClient.createHttpClient(httpLoggingInterceptor = get()) }
    single { ApiClient.createApiService(okHttpClient = get()) }
}