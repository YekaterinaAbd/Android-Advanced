package com.example.jokes.presentation.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokes.presentation.model.JokeModel
import com.example.jokes.presentation.utils.widgets.JokeView

interface OnJokeTypeCLickListener {
    fun onJokeTypeClick(type: String)
}

class JokesAdapter(
    val onJokeTypeCLickListener: OnJokeTypeCLickListener? = null,
    val byType: Boolean = false
) : RecyclerView.Adapter<JokesAdapter.JokesViewHolder>() {

    private val jokes = mutableListOf<JokeModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val view = JokeView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return JokesViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(0xDDD3EE)
        holder.bind(jokes[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return jokes.size
    }

    fun addJokes(items: List<JokeModel>?) {
        if (!items.isNullOrEmpty()) {
            jokes.addAll(items)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        jokes.clear()
        notifyDataSetChanged()
    }

    inner class JokesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(joke: JokeModel) {
            val jokeView = itemView as JokeView
            jokeView.setData(joke, byType)
            jokeView.getJokeTypeView().setOnClickListener {
                if (!joke.type.isNullOrEmpty()) {
                    onJokeTypeCLickListener?.onJokeTypeClick(joke.type)
                }
            }

        }
    }
}
