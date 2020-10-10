package com.example.jokes.domain.use_case

import com.example.jokes.domain.repository.JokesRepository

class GetJokesByTypeUseCase(
    private val jokesRepository: JokesRepository
) {
    suspend fun getJokesByType(type: String) = jokesRepository.getJokesByType(type)
}
