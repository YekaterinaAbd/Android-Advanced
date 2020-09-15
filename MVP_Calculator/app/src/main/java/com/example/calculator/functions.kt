package com.example.calculator
import com.example.calculator.model.Operator

fun String.toEnum(): Operator {
    return when (this) {
        "+" -> Operator.ADD
        "-" -> Operator.SUBTRACT
        "÷" -> Operator.DIVIDE
        "×" -> Operator.MULTIPLY
        else -> Operator.EMPTY
    }
}

fun String.cutString(): String {
    return if (this.isNotEmpty()) {
        this.substring(0, this.length - 1)
    } else ""
}
