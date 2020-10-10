package com.example.jokes.presentation

sealed class State {
    object ShowLoading : State()
    object HideLoading : State()
    data class Error(val error: String?) : State()
    data class Exception(val error: Throwable) : State()
}
