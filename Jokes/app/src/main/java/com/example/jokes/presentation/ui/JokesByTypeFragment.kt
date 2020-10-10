package com.example.jokes.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jokes.R
import com.example.jokes.presentation.State
import com.example.jokes.presentation.utils.JOKE_TYPE
import org.koin.android.ext.android.inject

class JokesByTypeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var type: String? = null

    private val viewModel: JokesViewModel by inject()

    private val adapter by lazy {
        JokesAdapter(byType = true)
    }

    override fun setArguments(args: Bundle?) {
        super.setArguments(args)
        type = args?.getString(JOKE_TYPE)
        (activity as MainActivity?)?.setActionBarTitle("#$type")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jokes_by_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        observe()
        getJokes()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).setActionBarTitle("Jokes")
    }

    private fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        type?.let { (activity as MainActivity?)?.setActionBarTitle(it) }

        swipeRefreshLayout.setOnRefreshListener {
            adapter.clear()
            getJokes()
        }
    }

    private fun setAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun getJokes() {
        type?.let { viewModel.getJokesByType(it) }
    }

    private fun observe() {
        viewModel.commonLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is State.ShowLoading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                is State.HideLoading -> {
                    swipeRefreshLayout.isRefreshing = false
                }
                is State.Error -> {
                    Toast.makeText(context, "Ups, something went wrong..", Toast.LENGTH_SHORT)
                        .show()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        viewModel.jokesListLiveData.observe(viewLifecycleOwner, { result ->
            if (!result.isNullOrEmpty())
                adapter.addJokes(result)
        })
    }
}
