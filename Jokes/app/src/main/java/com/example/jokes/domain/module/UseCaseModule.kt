package com.example.jokes.domain.module

import com.example.jokes.domain.use_case.GetJokesByTypeUseCase
import com.example.jokes.domain.use_case.GetJokesListUseCase
import com.example.jokes.domain.use_case.GetRandomJokeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetJokesByTypeUseCase(jokesRepository = get()) }
    factory { GetRandomJokeUseCase(jokesRepository = get()) }
    factory { GetJokesListUseCase(jokesRepository = get()) }
}