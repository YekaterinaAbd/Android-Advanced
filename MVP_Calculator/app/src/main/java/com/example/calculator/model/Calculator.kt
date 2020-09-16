package com.example.calculator.model

import com.example.calculator.contract.Contract
import com.example.calculator.utils.ERROR
import com.example.calculator.utils.nf

class Calculator : Contract.Model {
    var firstNumber: String = ""
    var secondNumber: String = ""
    var operator: Operator = Operator.EMPTY
    var state: State = State.EMPTY

    override fun add(): String = nf.format(firstNumber.toDouble() + secondNumber.toDouble())

    override fun subtract(): String = nf.format(firstNumber.toDouble() - secondNumber.toDouble())

    override fun multiply(): String = nf.format(firstNumber.toDouble() * secondNumber.toDouble())

    override fun divide(): String {
       val answer = firstNumber.toDouble() / secondNumber.toDouble()
        return if (answer == Double.POSITIVE_INFINITY || answer == Double.NEGATIVE_INFINITY) {
            ERROR
        } else nf.format(answer)
    }
}
