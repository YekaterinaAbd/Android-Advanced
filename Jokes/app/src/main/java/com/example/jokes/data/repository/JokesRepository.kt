package com.example.jokes.data.repository

import com.example.jokes.data.api.JokeApi
import com.example.jokes.data.model.Joke

interface JokesRepository {
    suspend fun getTenJokes(): List<Joke>?
    suspend fun getRandomJoke(): Joke?
    suspend fun getJokesByType(type: String): List<Joke>?
}

class JokesRepositoryImpl(
    private val service: JokeApi
) : JokesRepository {
    override suspend fun getTenJokes(): List<Joke>? {
        return service.getJokes().body()
    }

    override suspend fun getRandomJoke(): Joke? {
        return service.getJoke().body()
    }

    override suspend fun getJokesByType(type: String): List<Joke>? {
        return service.getJokesByType(type).body()
    }
}
