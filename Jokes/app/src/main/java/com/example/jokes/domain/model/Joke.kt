package com.example.jokes.domain.model

data class Joke(
    val id: Int? = null,
    val type: String? = null,
    val setup: String? = null,
    val punchline: String? = null
)
