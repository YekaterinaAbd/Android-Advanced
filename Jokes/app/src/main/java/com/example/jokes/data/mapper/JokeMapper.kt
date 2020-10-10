package com.example.jokes.data.mapper

import com.example.jokes.data.model.JokeData
import com.example.jokes.domain.Mapper
import com.example.jokes.domain.model.Joke

class JokeMapper: Mapper<Joke, JokeData> {
    override fun from(model: Joke): JokeData = with(model){
        return JokeData(
            id = id,
            type = type,
            setup = setup,
            punchline = punchline
        )
    }

    override fun to(model: JokeData): Joke = with(model){
        return Joke(
            id = id,
            type = type,
            setup = setup,
            punchline = punchline
        )
    }
}