package com.example.calculator.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R
import com.example.calculator.contract.Contract
import com.example.calculator.presenter.MainPresenter
import com.example.calculator.utils.ERROR
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Contract.View {

    private val presenter: MainPresenter by lazy {
        MainPresenter(this)
    }

    private val numberButtonClick = View.OnClickListener { v ->
        val number: String? = (v as Button).text as String?
        presenter.processNumber(number)
    }

    private val operandButtonClick = View.OnClickListener { v ->
        val operator: String? = (v as Button).text as String?
        if (!operator.isNullOrEmpty())
            presenter.processOperator(operator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindViews()
    }

    override fun displayAnswer(answer: String) {
        textWindow.text = answer
    }

    override fun appendWindowText(text: String?) {
        if (!text.isNullOrEmpty()) {
            textWindow.append(text)
        }
    }

    override fun eraseSymbol() {
        if (!textWindow.text.isNullOrEmpty()) {
            textWindow.text = textWindow.text.subSequence(0, textWindow.text.length - 1)
        }
    }

    override fun clearWindow() {
        textWindow.text = ""
    }

    private fun bindViews() {
        btn0.setOnClickListener(numberButtonClick)
        btn1.setOnClickListener(numberButtonClick)
        btn2.setOnClickListener(numberButtonClick)
        btn3.setOnClickListener(numberButtonClick)
        btn4.setOnClickListener(numberButtonClick)
        btn5.setOnClickListener(numberButtonClick)
        btn6.setOnClickListener(numberButtonClick)
        btn7.setOnClickListener(numberButtonClick)
        btn8.setOnClickListener(numberButtonClick)
        btn9.setOnClickListener(numberButtonClick)
        btnPlus.setOnClickListener(operandButtonClick)
        btnMinus.setOnClickListener(operandButtonClick)
        btnDivide.setOnClickListener(operandButtonClick)
        btnMultiply.setOnClickListener(operandButtonClick)

        btnC.setOnClickListener {
            presenter.processWindowCleared()
        }
        btnBackspace.setOnClickListener {
            presenter.processBackspace()
        }
        btnEqual.setOnClickListener {
            presenter.calculateAnswer()
        }
    }
}
