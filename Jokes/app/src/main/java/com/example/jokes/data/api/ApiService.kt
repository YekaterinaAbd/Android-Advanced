package com.example.jokes.data.api

import com.example.jokes.data.model.JokeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JokeApi {
    @GET("random_ten")
    suspend fun getJokes(): Response<List<JokeData>>

    @GET("random_joke")
    suspend fun getJoke(): Response<JokeData>

    @GET("jokes/{type}/ten")
    suspend fun getJokesByType(
        @Path("type") type: String
    ): Response<List<JokeData>>
}
