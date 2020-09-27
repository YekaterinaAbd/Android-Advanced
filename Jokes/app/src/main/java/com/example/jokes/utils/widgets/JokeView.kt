package com.example.jokes.utils.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.jokes.R
import com.example.jokes.data.model.Joke


class JokeView : CardView {

    private lateinit var punchline: TextView
    private lateinit var setup: TextView
    private lateinit var type: TextView
    private lateinit var arrow: ImageView

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    fun setData(joke: Joke, byType: Boolean) {
        punchline.text = joke.punchline
        setup.text = joke.setup
        if(!byType) type.text = context.getString(R.string.tag, joke.type)
        else type.visibility = View.GONE
        punchline.visibility = View.GONE
        arrow.setImageResource(R.drawable.ic_down_arrow)

        this.setOnClickListener {
            if (!joke.closed) {
                punchline.visibility = View.VISIBLE
                arrow.setImageResource(R.drawable.ic_up_arrow)
                joke.closed = true
            } else {
                punchline.visibility = View.GONE
                arrow.setImageResource(R.drawable.ic_down_arrow)
                joke.closed = false
            }
        }
    }

    fun getJokeTypeView(): View {
        return type
    }

    private fun init(context: Context) {
        inflate(context, R.layout.joke_view, this)

        punchline = findViewById(R.id.punchline)
        setup = findViewById(R.id.setup)
        type = findViewById(R.id.type)
        arrow = findViewById(R.id.arrow)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        init(context)
    }
}


