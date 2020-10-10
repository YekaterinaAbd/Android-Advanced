package com.example.jokes.domain.repository

import com.example.jokes.data.Result
import com.example.jokes.domain.model.Joke

interface JokesRepository {
    suspend fun getJokesList(): Result<List<Joke>>
    suspend fun getRandomJoke(): Joke
    suspend fun getJokesByType(type: String): Result<List<Joke>>
}
