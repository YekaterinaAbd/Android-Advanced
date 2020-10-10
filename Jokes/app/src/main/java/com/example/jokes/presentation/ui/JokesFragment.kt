package com.example.jokes.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.jokes.R
import com.example.jokes.presentation.State
import com.example.jokes.presentation.model.JokeModel
import com.example.jokes.presentation.utils.JOKE_TYPE
import org.koin.android.ext.android.inject

class JokesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var button: Button

    private val onJokeTypeCLickListener = object : OnJokeTypeCLickListener {
        override fun onJokeTypeClick(type: String) {
            val bundle = Bundle()
            bundle.putString(JOKE_TYPE, type)
            val jokesByTypeFragment = JokesByTypeFragment()
            jokesByTypeFragment.arguments = bundle

            parentFragmentManager.beginTransaction().add(R.id.main, jokesByTypeFragment)
                .addToBackStack(null).commit()
        }
    }

    private val viewModel: JokesViewModel by inject()

    private val adapter by lazy {
        JokesAdapter(onJokeTypeCLickListener)
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
            getRandomJoke()
        }
    }

    private fun setAdapter() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun getJokes() = viewModel.getTenJokes()

    private fun getRandomJoke() {
        viewModel.getRandomJoke()

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

        viewModel.jokeLiveData.observe(viewLifecycleOwner, { result ->
            if (result != null)
                showAlert(result)
        })

    }


    private fun showAlert(joke: JokeModel) {
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
