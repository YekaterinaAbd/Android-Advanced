package com.example.jokes.presentation.ui

import androidx.lifecycle.MutableLiveData
import com.example.jokes.data.Result
import com.example.jokes.domain.use_case.GetJokesByTypeUseCase
import com.example.jokes.domain.use_case.GetJokesListUseCase
import com.example.jokes.domain.use_case.GetRandomJokeUseCase
import com.example.jokes.presentation.State
import com.example.jokes.presentation.base.BaseViewModel
import com.example.jokes.presentation.model.JokeModel
import com.example.jokes.presentation.model.JokeModelMapper
import kotlinx.coroutines.launch

class JokesViewModel(
    private val jokesListUseCase: GetJokesListUseCase,
    private val jokesByTypeUseCase: GetJokesByTypeUseCase,
    private val jokeUseCase: GetRandomJokeUseCase,
    private val jokeModelMapper: JokeModelMapper
) : BaseViewModel() {

    val commonLiveData = MutableLiveData<State>()
    val jokesListLiveData = MutableLiveData<List<JokeModel>>()
    val jokeLiveData = MutableLiveData<JokeModel>()

    fun getTenJokes() {
        launch {
            commonLiveData.value = State.ShowLoading
            when (val result = jokesListUseCase.getJokesList()) {
                is Result.Success -> {
                    val jokeModels = result.data.map { jokeModelMapper.from(it) }
                    jokesListLiveData.value = jokeModels
                }
                is Result.Error ->
                    result.throwable.message?.let {
                        commonLiveData.value = State.Error(it)
                    }
            }
            commonLiveData.value = State.HideLoading
        }
    }

    fun getJokesByType(type: String) {
        launch {
            commonLiveData.value = State.ShowLoading
            when (val result = jokesByTypeUseCase.getJokesByType(type)) {
                is Result.Success -> {
                    val jokeModels = result.data.map { jokeModelMapper.from(it) }
                    jokesListLiveData.value = jokeModels
                }
                is Result.Error ->
                    result.throwable.message?.let {
                        commonLiveData.value = State.Error(it)
                    }
            }
            commonLiveData.value = State.HideLoading
        }
    }

    fun getRandomJoke() {
        launch {
            commonLiveData.value = State.ShowLoading
            try {
                val result = jokeUseCase.getRandomJoke()
                val jokeModel = jokeModelMapper.from(result)
                jokeLiveData.value = jokeModel
            } catch (e: Throwable) {
                commonLiveData.value = State.Error(e.message)
            }
            commonLiveData.value = State.HideLoading
        }
    }
}
