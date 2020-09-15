package com.example.calculator.model

enum class Operator {
    EMPTY,
    ADD,
    SUBTRACT,
    DIVIDE,
    MULTIPLY,
    ANSWER
}

fun String.toEnum(): Operator {
    return when (this) {
        "+" -> Operator.ADD
        "-" -> Operator.SUBTRACT
        "÷" -> Operator.DIVIDE
        "×" -> Operator.MULTIPLY
        else -> Operator.EMPTY
    }
}