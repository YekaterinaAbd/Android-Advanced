package com.example.jokes.data.repository

import com.example.jokes.data.Result
import com.example.jokes.data.api.JokeApi
import com.example.jokes.data.mapper.JokeMapper
import com.example.jokes.data.model.JokeData
import com.example.jokes.domain.model.Joke
import com.example.jokes.domain.repository.JokesRepository

class JokesRepositoryImpl(
    private val service: JokeApi,
    private val jokeMapper: JokeMapper
) : JokesRepository {

    override suspend fun getJokesList(): Result<List<Joke>> {
        return try {
            val response = service.getJokes()
            val list = response.body()?.map { jokeMapper.to(it) } ?: emptyList()
            Result.Success(list)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getJokesByType(type: String): Result<List<Joke>> {
        return try {
            val response = service.getJokesByType(type)
            val list = response.body()?.map { jokeMapper.to(it) } ?: emptyList()
            Result.Success(list)
        } catch (e: Throwable) {
            Result.Error(e)
        }
    }

    override suspend fun getRandomJoke(): Joke {
        val response = service.getJoke()
        return jokeMapper.to(model = response.body() ?: JokeData())
    }
}
