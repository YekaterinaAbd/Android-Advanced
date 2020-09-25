package com.example.jokes.view_model

import androidx.lifecycle.MutableLiveData
import com.example.jokes.base.BaseViewModel
import com.example.jokes.model.Joke
import com.example.jokes.utils.JokesRepository
import kotlinx.coroutines.launch

class JokesViewModel(private val jokesRepository: JokesRepository) : BaseViewModel() {

    var liveData = MutableLiveData<State>()

    fun getTenJokes() {
        launch {
            liveData.value = State.ShowLoading
            val jokes: List<Joke>? = try {
                jokesRepository.getTenJokes()
            } catch (e: Exception) {
                //liveData.value = State.Error
                emptyList()
            }
            liveData.value = State.JokesListResult(jokes)
            liveData.value = State.HideLoading
        }
    }

    fun getRandomJoke() {
        launch {
            liveData.value = State.ShowLoading
            val joke = try {
                jokesRepository.getRandomJoke()
            } catch (e: Exception) {
                liveData.value = State.Error
                null
            }
            liveData.value = State.JokeResult(joke)
            liveData.value = State.HideLoading
        }
    }

    sealed class State {
        data class JokeResult(val joke: Joke?) : State()
        data class JokesListResult(val jokes: List<Joke>?) : State()
        object Error : State()
        object HideLoading : State()
        object ShowLoading : State()
    }
}
