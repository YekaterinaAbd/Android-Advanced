package com.example.calculator.model

data class Calculator(
    var firstNumber: String = "",
    var secondNumber: String = "",
    var answer: Double? = null,
    var operator: Operator = Operator.EMPTY,
    var state: State = State.STATE_EMPTY
)
