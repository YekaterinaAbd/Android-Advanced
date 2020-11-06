package com.example.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlin.coroutines.CoroutineContext

class NewsDataSourceFactory(private val coroutineContext: CoroutineContext) :
    DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()
    private lateinit var newsDataSource: NewsDataSource

    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(coroutineContext)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }

    fun invalidate() {
        newsDataSource.invalidate()
    }
}
