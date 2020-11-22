package com.example.pagination

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: Adapter
    private lateinit var viewModel: NewsViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var error: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        bindViews()
        initList()
        initState()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.recycler_view)
        error = findViewById(R.id.txt_error)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun initList() {
        adapter = Adapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        viewModel.getNews()
        viewModel.getLiveData().observe(this, {
            adapter.submitList(it)
        })
    }

    private fun initState() {
        viewModel.getState().observe(this, { state ->
            progressBar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                adapter.setState(state ?: State.RESULT)
            }
        })
    }
}

