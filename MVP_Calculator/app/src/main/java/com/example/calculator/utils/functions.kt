package com.example.calculator.utils

import com.example.calculator.model.Operator
import java.text.DecimalFormat
import java.text.NumberFormat

const val ERROR = "Error"
val nf: NumberFormat = DecimalFormat("#.######")

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
    val string = nf.format(this.toDouble()).toString()
    return if (string.isNotEmpty()) {
        string.substring(0, string.length - 1)
    } else ""
}
