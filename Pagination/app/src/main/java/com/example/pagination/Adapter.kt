package com.example.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class Adapter : PagedListAdapter<News, RecyclerView.ViewHolder>(DiffUtilCallBack) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING

    companion object {
        val DiffUtilCallBack = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == DATA_VIEW_TYPE) {
            val view = inflater.inflate(R.layout.item_news, parent, false)
            NewsViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_list_footer, parent, false)
            LoadMoreViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) getItem(position)?.let {
            (holder as NewsViewHolder).bind(it)
        } else (holder as LoadMoreViewHolder).bind(state)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(news: News) {
            val title = itemView.findViewById<TextView>(R.id.title)
            val image = itemView.findViewById<ImageView>(R.id.image)

            title.text = news.title
            Picasso.get().load(news.image).into(image)
        }
    }

    class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(status: State?) {
            val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
            val error = itemView.findViewById<TextView>(R.id.txt_error)
            progressBar.visibility =
                if (status == State.LOADING) VISIBLE else View.INVISIBLE
            error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
        }
    }
}