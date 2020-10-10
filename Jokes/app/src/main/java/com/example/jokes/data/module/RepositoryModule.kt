package com.example.jokes.data.module

import com.example.jokes.data.mapper.JokeMapper
import com.example.jokes.data.repository.JokesRepositoryImpl
import com.example.jokes.domain.repository.JokesRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { JokeMapper() }
    single<JokesRepository>{
        JokesRepositoryImpl(
            service = get(),
            jokeMapper = get()
        )
    }
}