package com.example.calculator.contract

interface Contract {
    interface View {
        fun appendWindowText(text: String?)
        fun eraseSymbol()
        fun clearWindow()
        fun displayAnswer(answer: String)
    }

    interface Presenter {
        fun processOperator(operator: String)
        fun processNumber(number: String?)
        fun calculateAnswer()
        fun processBackspace()
        fun processWindowCleared()
    }

    interface Model {
        fun add(): String
        fun subtract(): String
        fun multiply(): String
        fun divide(): String
    }
}
