package com.example.calculator.presenter

import com.example.calculator.contract.Contract
import com.example.calculator.model.Calculator
import com.example.calculator.model.Operator
import com.example.calculator.model.State
import com.example.calculator.utils.ERROR
import com.example.calculator.utils.cutString
import com.example.calculator.utils.nf
import com.example.calculator.utils.toEnum

class MainPresenter(private val view: Contract.View) : Contract.Presenter {

    private val calc = Calculator()

    override fun processOperator(operator: String) {
        when (calc.state) {
            State.FIRST_NUMBER -> {
                setOperator(operator)
            }
            State.OPERATION -> {
                view.eraseSymbol()
                setOperator(operator)
            }
            else -> { }
        }
    }

    private fun setOperator(operator: String) {
        calc.operator = operator.toEnum()
        calc.state = State.OPERATION
        view.appendWindowText(operator)
    }

    override fun processNumber(number: String?) {
        if (!number.isNullOrEmpty()) {
            when (calc.state) {
                State.EMPTY -> {
                    view.clearWindow()
                    calc.state = State.FIRST_NUMBER
                    calc.firstNumber = number
                }
                State.OPERATION -> {
                    calc.state = State.SECOND_NUMBER
                    calc.secondNumber = number
                }
                State.FIRST_NUMBER -> {
                    calc.firstNumber += number
                }
                State.SECOND_NUMBER -> {
                    calc.secondNumber += number
                }
            }
        }
        view.appendWindowText(number)
    }

    override fun processBackspace() {
        when (calc.state) {
            State.EMPTY -> {
            }
            State.OPERATION -> {
                calc.operator = Operator.EMPTY
                calc.state = State.FIRST_NUMBER
            }
            State.FIRST_NUMBER -> {
                calc.firstNumber = eraseNumber(calc.firstNumber)
                if (calc.firstNumber.isEmpty()) {
                    calc.state = State.EMPTY
                }
            }
            State.SECOND_NUMBER -> {
                calc.secondNumber = calc.secondNumber.cutString()
                if (calc.secondNumber.isEmpty()) {
                    calc.state = State.OPERATION
                }
            }
        }
        view.eraseSymbol()
    }

    private fun eraseNumber(number: String): String {
        val lastIndex = number.length - 1
        return if (number[lastIndex] != '.') calc.firstNumber.cutString()
        else calc.firstNumber.substring(0, lastIndex)
    }

    override fun processWindowCleared() {
        calc.firstNumber = ""
        calc.secondNumber = ""
        calc.operator = Operator.EMPTY
        calc.state = State.EMPTY
        view.clearWindow()
    }

    override fun calculateAnswer() {
        when (calc.state) {
            State.FIRST_NUMBER -> {
                view.displayAnswer(nf.format(calc.firstNumber.toDouble()))
            }
            State.SECOND_NUMBER -> {
                makeCalculations()
            }
            else -> { }
        }
    }

    private fun makeCalculations() {
        if (calc.firstNumber.isNotEmpty() && calc.secondNumber.isNotEmpty()) {
            var isError = false
            when (calc.operator) {
                Operator.ADD -> calc.firstNumber = calc.add()

                Operator.SUBTRACT -> calc.firstNumber = calc.subtract()

                Operator.MULTIPLY -> calc.firstNumber = calc.multiply()

                Operator.DIVIDE -> {
                    calc.firstNumber = calc.divide()
                    if (calc.firstNumber == ERROR) isError = true
                }
                else -> { }
            }
            setAnswerState(isError)
        }
    }

    private fun setAnswerState(isError: Boolean) {
        view.displayAnswer(calc.firstNumber)
        calc.operator = Operator.EMPTY
        calc.secondNumber = ""

        if (isError) {
            calc.state = State.EMPTY
            calc.firstNumber = ""
        } else calc.state = State.FIRST_NUMBER
    }
}
