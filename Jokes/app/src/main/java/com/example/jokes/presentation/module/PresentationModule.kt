package com.example.jokes.presentation.module

import com.example.jokes.presentation.model.JokeModelMapper
import com.example.jokes.presentation.ui.JokesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        JokesViewModel(
            jokeModelMapper = get(),
            jokesByTypeUseCase = get(),
            jokesListUseCase = get(),
            jokeUseCase = get()
        )
    }
}

val mapperModule = module {
    factory { JokeModelMapper() }
}
