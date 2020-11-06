package com.example.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers

class NewsViewModel : ViewModel() {

    private var newsLiveData: LiveData<PagedList<News>>
    private val newsDataSourceFactory = NewsDataSourceFactory(Dispatchers.IO)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        newsLiveData = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        newsDataSourceFactory.newsDataSourceLiveData,
        NewsDataSource::state
    )

    fun getNews(): LiveData<PagedList<News>> = newsLiveData

    fun listIsEmpty(): Boolean {
        return newsLiveData.value?.isEmpty() ?: true
    }
}