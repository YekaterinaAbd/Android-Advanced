package com.example.calculator.contract

interface Contract {
    interface View {
        fun appendWindowText(text: String?)
        fun setWindowText(text: String?)
        fun eraseSymbol()
        fun clearWindow()
        fun displayAnswer(answer: String)
        fun displayErrorMessage()
    }

    interface Presenter {
        fun processOperator(operator: String)
        fun processNumber(number: String?)
        fun calculateAnswer()
        fun processBackspace()
        fun processWindowCleared()
    }
}
