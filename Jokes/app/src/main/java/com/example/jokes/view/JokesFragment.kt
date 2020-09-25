package com.example.jokes.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jokes.R
import com.example.jokes.data.RetrofitService
import com.example.jokes.model.Joke
import com.example.jokes.utils.JokesRepositoryImpl
import com.example.jokes.view_model.JokesViewModel

class JokesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var button: Button

    private lateinit var jokesRepository: JokesRepositoryImpl
    private lateinit var viewModel: JokesViewModel

    private val adapter by lazy {
        JokesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jokes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setViewModel()
        observe()
        getJokes()
    }

    private fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        button = view.findViewById(R.id.button)

        swipeRefreshLayout.setOnRefreshListener {
            adapter.clear()
            getJokes()
        }

        button.setOnClickListener {
            viewModel.getRandomJoke()
        }
    }

    private fun setAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun setViewModel() {
        jokesRepository = JokesRepositoryImpl(RetrofitService.getJokeApi())
        viewModel = JokesViewModel(jokesRepository)
    }

    private fun getJokes() {
        viewModel.getTenJokes()
    }

    private fun observe() {
        viewModel.liveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is JokesViewModel.State.JokesListResult -> {
                    if (!result.jokes.isNullOrEmpty())
                        adapter.addJokes(result.jokes)
                }
                is JokesViewModel.State.JokeResult -> {
                    if (result.joke != null) {
                        showAlert(result.joke)
                    }
                }
                is JokesViewModel.State.ShowLoading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                is JokesViewModel.State.HideLoading -> {
                    swipeRefreshLayout.isRefreshing = false
                }
                is JokesViewModel.State.Error -> {
                }
            }
        })
    }

    private fun showAlert(joke: Joke) {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.joke_detailed_view, null)
        view.apply {
            view.findViewById<TextView>(R.id.punchline).text = joke.punchline
            view.findViewById<TextView>(R.id.setup).text = joke.setup
        }
        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setPositiveButton(R.string.close) { _, _ ->
        }
        builder.show()
    }
}
