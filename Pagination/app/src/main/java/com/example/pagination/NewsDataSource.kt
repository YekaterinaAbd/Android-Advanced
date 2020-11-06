package com.example.pagination

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class NewsDataSource(
    private val coroutineContext: CoroutineContext
) : PageKeyedDataSource<Int, News>() {

    var state = MutableLiveData<State>()

    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    private val apiService = Network.getService()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        updateState(State.LOADING)
        scope.launch {
            try {
                val response = apiService.getNews(1, params.requestedLoadSize)
                when {
                    response.isSuccessful -> {
                        val newsList = response.body()?.news ?: listOf()
                        callback.onResult(newsList, null, 2)
                        updateState(State.RESULT)
                    }
                    else -> updateState(State.ERROR)
                }
            } catch (e: Exception) {
                Log.d("PostsDataSource", "Failed to fetch data!")
                updateState(State.ERROR)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        scope.launch {
            updateState(State.LOADING)
            try {
                val response = apiService.getNews(params.key, params.requestedLoadSize)
                when {
                    response.isSuccessful -> {
                        val newsList = response.body()?.news ?: listOf()
                        callback.onResult(newsList, params.key + 1)
                        updateState(State.RESULT)
                    }
                    else -> {
                        updateState(State.ERROR)
                    }
                }
            } catch (e: Exception) {
                updateState(State.ERROR)
                Log.d("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    fun getMutableState(): MutableLiveData<State> = state
}