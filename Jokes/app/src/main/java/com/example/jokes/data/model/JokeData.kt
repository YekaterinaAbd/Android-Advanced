package com.example.jokes.data.model

import com.google.gson.annotations.SerializedName

data class JokeData(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("setup") val setup: String? = null,
    @SerializedName("punchline") val punchline: String? = null
)
