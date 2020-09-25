package com.example.jokes.model

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("id") val id: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("setup") val setup: String?,
    @SerializedName("punchline") val punchline: String?,
    var closed: Boolean = true
)
