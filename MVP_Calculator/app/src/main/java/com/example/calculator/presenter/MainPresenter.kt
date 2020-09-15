package com.example.calculator.presenter

import com.example.calculator.contract.Contract
import com.example.calculator.model.Calculator
import com.example.calculator.model.Operator
import com.example.calculator.model.State
import com.example.calculator.model.toEnum
import java.text.DecimalFormat
import java.text.NumberFormat

class MainPresenter(private val view: Contract.View) : Contract.Presenter {

    private val calc = Calculator()
    private val nf: NumberFormat = DecimalFormat("#.######")

    override fun processOperator(operator: String) {
        when (calc.state) {
            State.STATE_EMPTY -> {
                if (calc.operator == Operator.ANSWER) {
                    setOperator(operator)
                }
            }
            State.STATE_FIRST_NUMBER -> {
                setOperator(operator)
            }
            State.STATE_OPERATION -> {
                view.eraseSymbol()
                setOperator(operator)
            }
            State.STATE_SECOND_NUMBER -> { }
        }
    }

    private fun setOperator(operator: String) {
        calc.operator = operator.toEnum()
        calc.state = State.STATE_OPERATION
        view.appendWindowText(operator)
    }

    override fun processNumber(number: String?) {
        if (!number.isNullOrEmpty()) {
            when (calc.state) {
                State.STATE_EMPTY -> {
                    view.clearWindow()
                    calc.firstNumber = number
                    calc.state = State.STATE_FIRST_NUMBER
                }
                State.STATE_OPERATION -> {
                    calc.secondNumber = number
                    calc.state = State.STATE_SECOND_NUMBER
                }
                State.STATE_FIRST_NUMBER -> {
                    calc.firstNumber += number
                }
                State.STATE_SECOND_NUMBER -> {
                    calc.secondNumber += number
                }
            }
        }
        view.appendWindowText(number)
    }


    override fun processBackspace() {
        when (calc.state) {
            State.STATE_EMPTY -> { }
            State.STATE_OPERATION -> {
                calc.operator = Operator.EMPTY
                calc.state = State.STATE_FIRST_NUMBER
            }
            State.STATE_FIRST_NUMBER -> {
                calc.firstNumber = calc.firstNumber.substring(0, calc.firstNumber.length - 1)
            }
            State.STATE_SECOND_NUMBER -> {
                calc.secondNumber = calc.secondNumber.substring(0, calc.secondNumber.length - 1)
            }
        }
        view.eraseSymbol()
    }

    override fun processWindowCleared() {
        calc.firstNumber = ""
        calc.secondNumber = ""
        calc.operator = Operator.EMPTY
        calc.state = State.STATE_EMPTY
        view.clearWindow()
    }

    override fun calculateAnswer() {
        when (calc.state) {
            State.STATE_FIRST_NUMBER -> {
                calc.answer = calc.firstNumber.toDouble()
                view.displayAnswer(nf.format(calc.answer))
            }
            State.STATE_SECOND_NUMBER -> {
                makeCalculations()
            }
            else -> { }
        }
    }

    private fun makeCalculations() {
        if (calc.firstNumber.isNotEmpty() && calc.secondNumber.isNotEmpty()) {
            when (calc.operator) {
                Operator.ADD -> {
                    calc.answer = calc.firstNumber.toDouble() + calc.secondNumber.toDouble()
                    view.displayAnswer(nf.format(calc.answer))
                }
                Operator.SUBTRACT -> {
                    calc.answer = calc.firstNumber.toDouble() - calc.secondNumber.toDouble()
                    view.displayAnswer(nf.format(calc.answer))
                }
                Operator.MULTIPLY -> {
                    calc.answer = calc.firstNumber.toDouble() * calc.secondNumber.toDouble()
                    view.displayAnswer(nf.format(calc.answer))
                }
                Operator.DIVIDE -> {
                    try {
                        calc.answer = calc.firstNumber.toDouble() / calc.secondNumber.toDouble()
                        view.displayAnswer(nf.format(calc.answer))
                    } catch (e: ArithmeticException) {
                        view.displayErrorMessage()
                    }
                }
                else -> { }
            }
            calc.firstNumber = calc.answer.toString()
            calc.operator = Operator.ANSWER
            calc.state = State.STATE_EMPTY
            calc.secondNumber = ""
        }
    }
}


