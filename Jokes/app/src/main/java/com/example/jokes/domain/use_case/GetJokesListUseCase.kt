package com.example.jokes.domain.use_case

import com.example.jokes.domain.repository.JokesRepository

class GetJokesListUseCase (
    private val jokesRepository: JokesRepository
) {
    suspend fun getJokesList() = jokesRepository.getJokesList()
}
