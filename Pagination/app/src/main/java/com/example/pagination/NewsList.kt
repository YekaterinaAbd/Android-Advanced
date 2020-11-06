package com.example.pagination

import com.google.gson.annotations.SerializedName

data class NewsList(

    @SerializedName("articles") val news: List<News>
)

data class News(
    val title: String,
    @SerializedName("urlToImage") val image: String
)
