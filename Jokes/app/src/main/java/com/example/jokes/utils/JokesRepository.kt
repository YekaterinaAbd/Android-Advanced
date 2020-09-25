package com.example.jokes.utils

import android.util.Log
import com.example.jokes.data.JokeApi
import com.example.jokes.data.RetrofitService
import com.example.jokes.model.Joke

interface JokesRepository {
    suspend fun getTenJokes(): List<Joke>?
    suspend fun getRandomJoke(): Joke?
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
}
