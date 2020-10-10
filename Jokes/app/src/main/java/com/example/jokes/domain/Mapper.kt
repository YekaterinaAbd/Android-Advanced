package com.example.jokes.domain

interface Mapper<N, M> {

    fun from(model: N): M

    fun to(model: M): N
}
