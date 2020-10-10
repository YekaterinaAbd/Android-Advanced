package com.example.jokes.presentation.model

import com.example.jokes.domain.Mapper
import com.example.jokes.domain.model.Joke

class JokeModelMapper : Mapper<Joke, JokeModel> {
    override fun from(model: Joke): JokeModel = with(model) {
        return JokeModel(
            id = id,
            type = type,
            setup = setup,
            punchline = punchline
        )
    }

    override fun to(model: JokeModel): Joke = with(model) {
        return Joke(
            id = id,
            type = type,
            setup = setup,
            punchline = punchline
        )
    }
}