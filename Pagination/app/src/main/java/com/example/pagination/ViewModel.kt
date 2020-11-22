package com.example.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.Dispatchers

class NewsViewModel : ViewModel() {

    private val config: PagedList.Config
    private lateinit var newsLiveData: LiveData<PagedList<News>>
    private lateinit var newsDataSourceFactory: NewsDataSourceFactory

    init {
        config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
    }

    fun getNews(){
        newsDataSourceFactory = NewsDataSourceFactory(Dispatchers.IO)
        newsLiveData = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        newsDataSourceFactory.newsDataSourceLiveData,
        NewsDataSource::state
    )

   fun getLiveData(): LiveData<PagedList<News>> = newsLiveData

    fun listIsEmpty(): Boolean {
        return newsLiveData.value?.isEmpty() ?: true
    }
}