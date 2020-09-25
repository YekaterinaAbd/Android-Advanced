package com.example.jokes.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jokes.model.Joke
import com.example.jokes.utils.JokeView

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.JokesViewHolder>() {

    private val jokes = mutableListOf<Joke>()

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

    fun addJokes(items: List<Joke>?) {
        if (!items.isNullOrEmpty()) {
            jokes.addAll(items)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        jokes.clear()
        notifyDataSetChanged()
    }

    class JokesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(joke: Joke) {
            (itemView as JokeView).setData(joke)
        }
    }
}
