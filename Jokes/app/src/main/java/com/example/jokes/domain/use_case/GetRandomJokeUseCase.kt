package com.example.jokes.domain.use_case

import com.example.jokes.domain.repository.JokesRepository

class GetRandomJokeUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend fun getRandomJoke() = jokesRepository.getRandomJoke()
}
