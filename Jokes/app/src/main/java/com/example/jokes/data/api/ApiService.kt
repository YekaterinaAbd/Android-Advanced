package com.example.jokes.data.api

import com.example.jokes.data.model.Joke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JokeApi {
    @GET("random_ten")
    suspend fun getJokes(): Response<List<Joke>>

    @GET("random_joke")
    suspend fun getJoke(): Response<Joke>

    @GET("jokes/{type}/ten")
    suspend fun getJokesByType(
        @Path("type") type: String
    ): Response<List<Joke>>
}
